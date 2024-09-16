package com.demoboletto.service;

import com.demoboletto.domain.Travel;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserTravel;
import com.demoboletto.dto.request.CreateTravelDto;
import com.demoboletto.dto.request.UpdateTravelDto;
import com.demoboletto.dto.response.GetTravelDto;
import com.demoboletto.repository.TravelRepository;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.repository.UserTravelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;
    private final UserTravelRepository userTravelRepository;
    private final UserRepository userRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
    private final ZonedDateTime nowKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public boolean createTravelList(CreateTravelDto travelDto) {
        // check if travel data exists
        for (Long memberId : travelDto.members()) {
            for (Travel travel : userTravelRepository.findTravelsByUserId(memberId)) {
                if (isOverlapping(travel.getStartDate(),travel.getEndDate(),travelDto.startDate(),travelDto.endDate())) {
                    return false;
                }
            }
        }
        // insert new travel data in db
        Travel travel = travelRepository.save(Travel.create(travelDto));
        // get member id from travelDto, and insert into UserTravel table
        travelDto.members().forEach(memberId -> {
            Optional<User> user = userRepository.findById(memberId);
            if (!user.isPresent()) {
                return;
            }
            // insert user object into UserTravel table
            userTravelRepository.save(UserTravel.create(user.get(), travel));
        });
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
    private boolean isOverlapping(String preStart, String preEnd, String start, String end) {
        LocalDateTime preStartDate = LocalDateTime.parse(preStart, formatter);
        LocalDateTime preEndDate = LocalDateTime.parse(preEnd, formatter);
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);
        return (startDate.isBefore(preEndDate) && endDate.isAfter(preStartDate));
    }
    private GetTravelDto convertToGetTravelDto(Travel travel) {
        return GetTravelDto.builder()
                .travelId(travel.getTravelId())
                .departure(travel.getDeparture())
                .arrive(travel.getArrive())
                .keyword(travel.getKeyword())
                .startDate(travel.getStartDate())
                .endDate(travel.getEndDate())
                .members(userTravelRepository.findUsersByTravelId(travel.getTravelId()))
                .color(travel.getColor())
                .build();
    }
    @Transactional
    public GetTravelDto updateTravelList(UpdateTravelDto travelDto) {
        // get travel data from db
        Travel preTravel = travelRepository.findById(travelDto.travelId())
                .orElseThrow(() -> new EntityNotFoundException("travel data not found"));

        // check if travel data exists
        for (Long memberId : travelDto.members()) {
            for (Travel travel : userTravelRepository.findTravelsByUserId(memberId)) {
                if (isOverlapping(travel.getStartDate(),travel.getEndDate(),travelDto.startDate(),travelDto.endDate())) {
                    if (!travel.getTravelId().equals(travelDto.travelId())) return null;
                }
            }
        }
        // update travel data
        Travel postTravel = travelRepository.save(preTravel.update(travelDto));

        // if member list is changed, update UserTravel table

        //get user list in UserTravel table
        userTravelRepository.findAllByTravelId(travelDto.travelId()).forEach(member -> {
            // if member is not in the new member list, delete the member from UserTravel table
            if (!travelDto.members().contains(member.getId())) {
                userTravelRepository.delete(userTravelRepository.findByUserIdAndTravelId(member.getId(), travelDto.travelId()));
            } else {
            // if member is in the new member list, delete the member from the new member list
                travelDto.members().remove(member.getId());
            }
        });
        // insert new member list into UserTravel table
        travelDto.members().forEach(memberId -> {
            Optional<User> user = userRepository.findById(memberId);
            if (!user.isPresent()) {
                return;
            }
            // insert user object into UserTravel table
            userTravelRepository.save(UserTravel.create(user.get(), postTravel));
        });
        return convertToGetTravelDto(postTravel);
    }
    @Transactional
    public boolean deleteTravelList(Long travelId) {
        // delete travel data
        try {
            travelRepository.deleteById(travelId);
            // delete user data in UserTravel table
            userTravelRepository.deleteAllByTravelId(travelId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}