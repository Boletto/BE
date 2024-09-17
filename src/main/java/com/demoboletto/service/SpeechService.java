package com.demoboletto.service;

import com.demoboletto.domain.Picture;
import com.demoboletto.domain.Speech;
import com.demoboletto.dto.request.CreatePictureDto;
import com.demoboletto.repository.PictureRepository;
import com.demoboletto.repository.SpeechRepository;
import com.demoboletto.repository.TravelRepository;
import com.demoboletto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeechService {
    private final SpeechRepository speechRepository;

    public List<Speech> getSpeechList(Long travelId) {
        return speechRepository.findAllByTravelId(travelId);
    }
}