package com.demoboletto.service;

import com.demoboletto.dto.response.GetPictureDto;
import com.demoboletto.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PictureService {
    private final PictureRepository pictureRepository;

    public List<GetPictureDto> getPicturesByTravelId(Long travelId) {
        List<GetPictureDto> pictureList = new ArrayList<>();
        pictureRepository.findAllByTraveld(travelId).forEach(picture -> {
            pictureList.add(
                    GetPictureDto.builder()
                            .travelId(travelId)
                            .pictureIdx(picture.getPictureIdx())
                            .pictureUrl(picture.getPictureUrl())
                            .build());
        });
        return pictureList;
    }
}