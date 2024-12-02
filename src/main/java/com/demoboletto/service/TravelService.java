package com.demoboletto.service;

import com.demoboletto.domain.Travel;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserTravel;
import com.demoboletto.dto.request.CreateTravelDto;
import com.demoboletto.dto.request.UpdateTravelDto;
import com.demoboletto.dto.request.UpdateTravelStatusDto;
import com.demoboletto.dto.response.GetTravelDto;
import com.demoboletto.dto.response.GetUserTravelDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.repository.travel.TravelRepository;
import com.demoboletto.repository.travel.UserTravelRepository;
import com.demoboletto.type.ETravelStatusType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;
    private final UserTravelRepository userTravelRepository;
    private final UserRepository userRepository;
    //    private final PictureService pictureService;
//    private final StickerService stickerService;
//    private final SpeechService speechService;
    private final ZonedDateTime nowKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    @Transactional
    public boolean createTravelList(CreateTravelDto travelDto, Long userId) {
        log.info("createTravelList: {}", travelDto);

        travelDto.members().add(userId);

        // check if travel data exists
        for (Long memberId : travelDto.members()) {
            Optional<User> user = userRepository.findById(memberId);
            if (user.isEmpty()) {
                throw new CommonException(ErrorCode.NOT_FOUND_USER);
            }

            // 기존 여행 기록을 가져옴
            List<Travel> travels = userTravelRepository.findTravelsByUserId(memberId);
            if (travels == null || travels.isEmpty()) {
                continue; // 여행 기록이 없으면 다음 멤버로 넘어감
            }

            for (Travel travel : travels) {
                if (travel.getStartDate() == null || travel.getEndDate() == null) {
                    continue;
                }

                if (isOverlapping(travel.getStartDate(), travel.getEndDate(), travelDto.startDate(), travelDto.endDate())) {
                    return false;
                }
            }
        }

        // insert new travel data in db
        Travel travel = travelRepository.save(Travel.create(travelDto));

        // get member id from travelDto, and insert into UserTravel table
        travelDto.members().forEach(memberId -> {
            Optional<User> user = userRepository.findById(memberId);
            user.ifPresent(u -> userTravelRepository.save(UserTravel.create(u, travel)));
        });

//        // 친구들에게 알림 전송
//        travelDto.members().forEach(memberId -> {
//            if (!memberId.equals(userId)) {
//                alarmService.sendFriendInviteAlarm(userId, memberId, travel);  // 친구들에게 알림 전송
//            }
//        });
        return true;
    }


    public GetTravelDto getTravelList(Long id) {
        Optional<Travel> travel = travelRepository.findById(id);
        if (travel.isPresent()) {
            return convertToGetTravelDto(travel.get());
        } else {
            return null;    // return empty object
        }
    }

    public List<GetTravelDto> getAllTravelList(Long userId) {
        List<Travel> travel = userTravelRepository.findTravelsByUserId(userId);
        List<GetTravelDto> travelList = new ArrayList<>();
        for (Travel t : travel) {
            travelList.add(convertToGetTravelDto(t));
        }
        return travelList;
    }

    private boolean isOverlapping(LocalDate preStartDate, LocalDate preEndDate, LocalDate startDate, LocalDate endDate) {
        try {
            if (preStartDate == null || preEndDate == null || startDate == null || endDate == null) {
                return false;  // 또는 적절한 예외 처리
            }

            return (startDate.isBefore(preEndDate) && endDate.isAfter(preStartDate));
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());  // 로그 출력
            return false;  // 또는 예외를 던지거나 다른 방식으로 처리
        }
    }

    private GetTravelDto convertToGetTravelDto(Travel travel) {
        return GetTravelDto.builder()
                .travelId(travel.getTravelId())
                .departure(travel.getDeparture())
                .arrive(travel.getArrive())
                .keyword(travel.getKeyword())
                .startDate(travel.getStartDate())
                .endDate(travel.getEndDate())
                .members(convertUser(userTravelRepository.findUsersByTravelId(travel.getTravelId())))
                .color(travel.getColor())
                .build();
    }

    @Transactional
    public GetTravelDto updateTravelList(UpdateTravelDto travelDto) {
        // get travel data from db
        Travel preTravel = travelRepository.findById(travelDto.travelId())
                .orElseThrow(() -> new EntityNotFoundException("travel data not found"));

        for (Long memberId : travelDto.members()) {
            Optional<User> user = userRepository.findById(memberId);
            if (user.isEmpty()) {
                throw new CommonException(ErrorCode.NOT_FOUND_USER);
            }

            // 기존 여행 기록을 가져옴
            List<Travel> travels = userTravelRepository.findTravelsByUserId(memberId);
            if (travels == null || travels.isEmpty()) {
                continue;
            }

            for (Travel travel : travels) {
                if (travel.getStartDate() == null || travel.getEndDate() == null) {
                    continue;
                }
                // 일정이 겹치는지 확인하고, 다른 여행과 겹치면 예외 던짐
                if (!travel.getTravelId().equals(travelDto.travelId())) {
                    if (isOverlapping(travel.getStartDate(), travel.getEndDate(), travelDto.startDate(), travelDto.endDate())) {
                        throw new IllegalArgumentException("The travel period overlaps with an existing travel for userId: " + memberId);
                    }
                }
            }
        }
        // 여행 데이터 업데이트
        Travel postTravel = travelRepository.save(preTravel.update(travelDto));

        // 기존 멤버 리스트 가져오기
        List<UserTravel> existingUserTravels = userTravelRepository.findAllByTravelId(travelDto.travelId());

        // 1. 기존 멤버 삭제
        existingUserTravels.forEach(existingUserTravel -> {
            if (!travelDto.members().contains(existingUserTravel.getUser().getId())) {
                userTravelRepository.delete(existingUserTravel);
            }
        });

        // 2. 새로운 멤버 추가
        travelDto.members().forEach(memberId -> {
            Optional<User> user = userRepository.findById(memberId);
            if (user.isPresent() && existingUserTravels.stream().noneMatch(ut -> ut.getUser().getId().equals(memberId))) {
                userTravelRepository.save(UserTravel.create(user.get(), postTravel));
            }
        });

        return convertToGetTravelDto(postTravel);
    }

    @Transactional
    public boolean deleteTravelList(Long travelId) {
        // delete travel data
        try {
            // delete user data in UserTravel table
            userTravelRepository.deleteAllByTravelId(travelId);
            // delete picture data in Picture table
//            pictureService.deleteAllByTravelId(travelId);
            // delete sticker data in Sticker table
//            stickerService.deleteAllByTravelId(travelId);
            // delete speech data in Speech table
//            speechService.deleteAllByTravelId(travelId);

            travelRepository.deleteById(travelId);

        } catch (Exception e) {
            return false;
        }
        return true;
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
        Travel travel = userTravelRepository.findByUserIdAndTravelId(userId, travelId)
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
}
