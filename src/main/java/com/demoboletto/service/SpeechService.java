package com.demoboletto.service;

import com.demoboletto.domain.Speech;
import com.demoboletto.domain.Travel;
import com.demoboletto.dto.request.CreateSpeechDto;
import com.demoboletto.repository.SpeechRepository;
import com.demoboletto.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeechService {
    private final SpeechRepository speechRepository;
    private final TravelRepository travelRepository;

    public List<Speech> getSpeechList(Long travelId) {
        return speechRepository.findAllByTravelId(travelId);
    }
    @Transactional
    public boolean createSpeech(List<CreateSpeechDto> speechList, Long travelId) {
        try {
            speechRepository.findAllByTravelId(travelId).forEach(speechRepository::delete);
            if (speechList.size() == 0) {
                return true;
            }
            speechRepository.saveAll(makeSpeechList(speechList, travelId));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public List<Speech> makeSpeechList(List<CreateSpeechDto> speechList, Long travelId) {
        List<Speech> newSpeechList = new ArrayList<>();
        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new IllegalArgumentException("travel not found"));
        for (int i = 0; i < speechList.size(); i++) {
            newSpeechList.add(
                    Speech.create(
                            speechList.get(i),
                            travel
                    )
            );
        }
        return newSpeechList;
    }
}