package com.demoboletto.service;

import com.demoboletto.dto.response.GetPictureDto;
import com.demoboletto.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PictureService {
    private final PictureRepository pictureRepository;

    public GetPictureDto getPicturesByTravelId(Long travelId) {
        GetPictureDto getPictureDto = GetPictureDto.builder()
                .travelId(travelId)
                .pictureIdx(new ArrayList<>())
                .pictureUrl(new ArrayList<>())
                .build();
        pictureRepository.findAllByTraveld(travelId).forEach(picture -> {
            getPictureDto.pictureIdx().add(picture.getPictureIdx());
            getPictureDto.pictureUrl().add(picture.getPictureUrl());
        });
        return getPictureDto;
    }
}