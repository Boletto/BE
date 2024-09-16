package com.demoboletto.service;

import com.demoboletto.domain.Picture;
import com.demoboletto.dto.request.CreatePictureDto;
import com.demoboletto.dto.response.GetPictureDto;
import com.demoboletto.repository.PictureRepository;
import com.demoboletto.repository.TravelRepository;
import com.demoboletto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PictureService {
    private final PictureRepository pictureRepository;
    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final AWSS3Service awsS3Service;

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
    @Transactional
    public boolean createPicture(CreatePictureDto createPictureDto) {
        //save image to s3, create picture object && save picture object to db
        try {
            pictureRepository.save(
                    Picture.create(awsS3Service.uploadFile(createPictureDto.pictureFiles()),
                            createPictureDto.pictureIdx(),
                            travelRepository.findById(createPictureDto.travelId())
                                    .orElseThrow(() -> new IllegalArgumentException("travel not found")),
                            userRepository.findById(createPictureDto.userId())
                                    .orElseThrow(() -> new IllegalArgumentException("user not found"))
                    )
            );
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}