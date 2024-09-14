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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;
    private final UserTravelRepository userTravelRepository;
    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Travel createTravelList(CreateTravelDto travelDto) {
        // insert new travel data in db
        Travel travel = travelRepository.save(Travel.create(travelDto));
        // get friend id from travelDto, and insert into UserTravel table
        travelDto.friends().forEach(friend -> {
            Optional<User> user = userRepository.findById(friend);
            if (!user.isPresent()) {
                return;
            }
            userTravelRepository.save(UserTravel.create(user.get(), travel));
        });
        return travel;
    }

    public GetTravelDto getTravelList(Long id) {
        Optional<Travel> travel = travelRepository.findById(id);
        if (travel.isPresent()) {
            return GetTravelDto.builder()
                    .travelId(travel.get().getTravelId())
                    .departure(travel.get().getDeparture())
                    .arrive(travel.get().getArrive())
                    .keyword(travel.get().getKeyword())
                    .startDate(travel.get().getStartDate())
                    .endDate(travel.get().getEndDate())
                    .status(travel.get().getStatus())
                    .owner(travel.get().getOwner())
                    .color(travel.get().getColor())
                    .build();
        } else {
            return null;    // return empty object
        }
    }
    public List<GetTravelDto> getAllTravelList() {
        List<Travel> travel = travelRepository.findAll();
        List<GetTravelDto> travelList = new ArrayList<>();
        for (Travel t : travel) {
            travelList.add(
                    GetTravelDto.builder()
                            .travelId(t.getTravelId())
                            .departure(t.getDeparture())
                            .arrive(t.getArrive())
                            .keyword(t.getKeyword())
                            .startDate(t.getStartDate())
                            .endDate(t.getEndDate())
                            .status(t.getStatus())
                            .owner(t.getOwner())
                            .color(t.getColor())
                            .build()
            );
        }
        return travelList;
    }
}