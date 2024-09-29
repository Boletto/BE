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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PictureService {
    private final PictureRepository pictureRepository;
    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final AWSS3Service awsS3Service;

    @Transactional
    public GetPictureDto createPicture(CreatePictureDto createPictureDto, MultipartFile file) {
        //save image to s3, create picture object && save picture object to db
        try {
            Picture savePicture = pictureRepository.save(
                    Picture.create(awsS3Service.uploadFile(file),
                            createPictureDto.pictureIdx(),
                            travelRepository.findById(createPictureDto.travelId())
                                    .orElseThrow(() -> new IllegalArgumentException("travel not found")),
                            userRepository.findById(createPictureDto.userId())
                                    .orElseThrow(() -> new IllegalArgumentException("user not found"))
                    )
            );
            return GetPictureDto.builder()
                    .pictureId(savePicture.getId())
                    .pictureIdx(savePicture.getPictureIdx())
                    .pictureUrl(savePicture.getPictureUrl())
                    .build();
        } catch (IOException e) {
            return null;
        }
    }
    @Transactional
    public boolean deletePicture(Long pictureId) {
        try {
            // remove file from s3
            String[] split = pictureRepository.findById(pictureId)
                    .orElseThrow(() -> new IllegalArgumentException("picture not found"))
                    .getPictureUrl()
                    .split("/");
            awsS3Service.deleteFile(split[split.length - 1]);
            // remove picture object from db
            pictureRepository.deleteById(pictureId);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<GetPictureDto> getPictureList(Long travelId) {
        List<GetPictureDto> pictureDtoList = new ArrayList<>();
        pictureRepository.findAllByTravelIdAndIsDeletedFalse(travelId)  // add a condition to filter out deleted pictures
                .forEach(picture ->
                pictureDtoList.add(
                        GetPictureDto.builder()
                                .pictureId(picture.getId())
                                .pictureUrl(picture.getPictureUrl())
                                .pictureIdx(picture.getPictureIdx())
                                .build()
                ));
        return pictureDtoList;
    }
    @Transactional
    public void deleteAllByTravelId(Long travelId) {
        pictureRepository.findAllByTravelId(travelId).forEach(picture -> {
            deletePicture(picture.getId());
        });
    }
}