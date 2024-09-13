package com.demoboletto.service;

import com.demoboletto.domain.Travel;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserTravel;
import com.demoboletto.dto.request.CreateTravelDto;
import com.demoboletto.repository.TravelRepository;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.repository.UserTravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Travel getAllTravelList(Long id) {
        Optional<Travel> travel = travelRepository.findById(id);
        if (travel.isPresent()) {
            return travel.get();
        } else {
            return null;    // return empty object
        }
    }
}