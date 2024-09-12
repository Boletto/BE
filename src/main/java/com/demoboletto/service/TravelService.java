package com.demoboletto.service;

import com.demoboletto.domain.Travel;
import com.demoboletto.dto.request.CreateTravelDto;
import com.demoboletto.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Travel createTravelList(CreateTravelDto travelDto) {
        // insert new travel data in db
        return travelRepository.save(Travel.create(travelDto));
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