package com.demoboletto.service;

import com.demoboletto.domain.Travel;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserTravel;
import com.demoboletto.dto.request.CreateTravelDto;
import com.demoboletto.dto.response.GetTravelDto;
import com.demoboletto.repository.TravelRepository;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.repository.UserTravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            for (Travel travel : userTravelRepository.findAllByUserId(memberId)) {
                if (IsTravelCurrent(travel)) {
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
    public List<GetTravelDto> getAllTravelList() {
        List<Travel> travel = travelRepository.findAll();
        List<GetTravelDto> travelList = new ArrayList<>();
        for (Travel t : travel) {
            travelList.add(convertToGetTravelDto(t));
        }
        return travelList;
    }
    private boolean IsTravelCurrent(Travel travel) {
        if (LocalDateTime
                .parse(travel.getStartDate(), formatter)
                .atZone(ZoneId.of("Asia/Seoul")).isBefore(nowKorea)) {
            return LocalDateTime
                    .parse(travel.getEndDate(), formatter)
                    .atZone(ZoneId.of("Asia/Seoul")).isAfter(nowKorea);
        }
        return false;
    }
    private GetTravelDto convertToGetTravelDto(Travel travel) {
        return GetTravelDto.builder()
                .travelId(travel.getTravelId())
                .departure(travel.getDeparture())
                .arrive(travel.getArrive())
                .keyword(travel.getKeyword())
                .startDate(travel.getStartDate())
                .endDate(travel.getEndDate())
                .members(userTravelRepository.findAllByTravelId(travel.getTravelId()))
                .color(travel.getColor())
                .build();
    }
}