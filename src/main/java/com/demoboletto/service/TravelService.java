package com.demoboletto.service;

import com.demoboletto.domain.SysTicket;
import com.demoboletto.domain.Travel;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserTravel;
import com.demoboletto.dto.request.CreateTravelDto;
import com.demoboletto.dto.request.UpdateTravelDto;
import com.demoboletto.dto.request.UpdateTravelStatusDto;
import com.demoboletto.dto.response.GetTravelDto;
import com.demoboletto.dto.response.GetUserTravelDto;
import com.demoboletto.dto.response.TicketInfoDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.SysTicketRepository;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.repository.travel.TravelRepository;
import com.demoboletto.repository.travel.UserTravelRepository;
import com.demoboletto.type.ETravelStatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;
    private final UserTravelRepository userTravelRepository;
    private final UserRepository userRepository;
    private final AlarmService alarmService;
    private final SysTicketRepository sysTicketRepository;

    @Transactional
    public void createTravel(CreateTravelDto travelDto, Long userId) {
        log.info("createTravelList: {}", travelDto);

        // insert new travel data in db
        Travel travel = Travel.create(travelDto);
        SysTicket sysTicket = sysTicketRepository.randomEventTicket()
                .orElseGet(() -> sysTicketRepository.randomTicket()
                        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TICKET)));
        travel.setSysTicket(sysTicket);
        travelRepository.save(travel);

        List<Long> members = travelDto.members();
        members.add(userId);
        List<UserTravel> userTravels = members.stream().map(memberId -> {
            User user = userRepository.findById(memberId)
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
            return UserTravel.create(user, travel);
        }).toList();

        userTravelRepository.saveAll(userTravels);

        // remove self
        members.remove(userId);

        acceptTravel(travel.getTravelId(), userId);

        // 여행 초대 알림 전송
        alarmService.travelInviteFriends(travel, userId, members);

    }

    public List<GetTravelDto> getAllTravels(Long userId, boolean isAccepted) {
        List<UserTravel> userTravels = userTravelRepository.findUserTravelsByUserIdAndAccepted(userId, isAccepted);
        return userTravels.stream().map(this::convertToGetTravelDto).toList();
    }


    private GetTravelDto convertToGetTravelDto(UserTravel userTravel) {
        Travel travel = userTravel.getTravel();
        return GetTravelDto.builder()
                .travelId(travel.getTravelId())
                .ticketInfo(TicketInfoDto.of(travel.getSysTicket()))
                .departure(travel.getDeparture())
                .arrive(travel.getArrive())
                .keyword(travel.getKeyword())
                .startDate(travel.getStartDate())
                .endDate(travel.getEndDate())
                .createdDate(userTravel.getCreatedDate())
                .members(convertUser(userTravelRepository.findUsersByTravelId(travel.getTravelId())))
                .currentEditUserId(travel.getEditableUser() == null ? null : travel.getEditableUser().getId())
                .build();
    }

    private GetTravelDto convertToGetTravelDto(Travel travel) {
        return GetTravelDto.builder()
                .travelId(travel.getTravelId())
                .ticketInfo(TicketInfoDto.of(travel.getSysTicket()))
                .departure(travel.getDeparture())
                .arrive(travel.getArrive())
                .keyword(travel.getKeyword())
                .startDate(travel.getStartDate())
                .endDate(travel.getEndDate())
                .createdDate(travel.getCreatedDate())
                .members(convertUser(userTravelRepository.findUsersByTravelId(travel.getTravelId())))
                .currentEditUserId(travel.getEditableUser().getId())
                .status(travel.getStatus())
                .build();
    }

    @Transactional
    public void updateTravel(Long userId, Long travelId, UpdateTravelDto travelDto) {
        // get travel data from db
        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TRAVEL));

        travel.update(travelDto);
        travelDto.members().add(userId);
        // 기존 멤버 리스트 가져오기
        List<UserTravel> userTravels = userTravelRepository.findAllByTravelId(travelId);

        userTravels.forEach(userTravel -> {
            if (!travelDto.members().contains(userTravel.getUser().getId())) {
                userTravelRepository.delete(userTravel);
            }
        });

        List<Long> newMembers = new ArrayList<>();

        travelDto.members().forEach(memberId -> {
            User user = userRepository.findById(memberId)
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
            UserTravel userTravel = userTravelRepository.findByUserIdAndTravelId(memberId, travelId)
                    .orElseGet(() -> {
                        newMembers.add(memberId);
                        return UserTravel.create(user, travel);
                    });
            userTravelRepository.save(userTravel);
        });
        alarmService.travelInviteFriends(travel, userId, newMembers);
    }

    @Transactional
    public void deleteTravel(Long userId, Long travelId) {
        UserTravel userTravel = userTravelRepository.findByUserIdAndTravelId(userId, travelId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        userTravelRepository.delete(userTravel);
    }

    private List<GetUserTravelDto> convertUser(List<User> userList) {
        List<GetUserTravelDto> resultList = new ArrayList<>();
        userList.forEach(user -> {
            resultList.add(
                    GetUserTravelDto.builder()
                            .name(user.getName())
                            .nickname(user.getNickname())
                            .userProfile(user.getUserProfile())
                            .userId(user.getId())
                            .build()
            );
        });
        return resultList;
    }

    public void updateTravelEditable(Long userId, Long travelId, UpdateTravelStatusDto updateTravelStatusDto) {
        User user = getUser(userId);
        Travel travel = travelRepository.findByUserIdAndTravelId(userId, travelId)
                .orElseThrow(() -> new CommonException(ErrorCode.ACCESS_DENIED));
        if (updateTravelStatusDto.getStatus() == ETravelStatusType.LOCK) {
            travel.lock(user);
        } else if (updateTravelStatusDto.getStatus() == ETravelStatusType.UNLOCK) {
            travel.unlock();
        }
        travelRepository.save(travel);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }

    public GetTravelDto getTravel(Long travelId) {
        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        return convertToGetTravelDto(travel);
    }

    public void acceptTravel(Long travelId, Long userId) {
        UserTravel userTravel = userTravelRepository.findByUserIdAndTravelId(userId, travelId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        if (userTravel.isAccepted()) {
            throw new CommonException(ErrorCode.ALREADY_ACCEPTED_TRAVEL);
        }
        Travel travel = userTravel.getTravel();
        LocalDate startDate = travel.getStartDate();
        LocalDate endDate = travel.getEndDate();
        if (travelRepository.existsAcceptedTravelByTravelDates(userId, startDate, endDate)) {
            throw new CommonException(ErrorCode.TRAVEL_OVERLAP);
        }
        userTravel.acceptInvite();
        userTravelRepository.save(userTravel);
    }

    public void rejectTravel(Long travelId, Long userId) {
        UserTravel userTravel = userTravelRepository.findByUserIdAndTravelId(userId, travelId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        userTravelRepository.delete(userTravel);
    }
}
