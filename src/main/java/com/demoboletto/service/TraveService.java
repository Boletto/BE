package com.demoboletto.service;

import com.demoboletto.domain.Travel;
import com.demoboletto.dto.request.CreateTravelDto;
import com.demoboletto.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
}