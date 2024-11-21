//package com.demoboletto.service;
//
//
//import com.demoboletto.domain.Picture;
//import com.demoboletto.dto.request.CreatePictureDto;
//import com.demoboletto.dto.request.CreatePictureFourCutDto;
//import com.demoboletto.dto.request.DeletePictureDto;
//import com.demoboletto.dto.response.GetFourCutDto;
//import com.demoboletto.dto.response.GetPictureDto;
//import com.demoboletto.repository.CollectRepository;
//import com.demoboletto.repository.PictureRepository;
//import com.demoboletto.repository.UserRepository;
//import com.demoboletto.repository.travel.TravelRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class PictureService {
//    private final PictureRepository pictureRepository;
//    private final TravelRepository travelRepository;
//    private final UserRepository userRepository;
//    private final ObjectStorageService ObjectStorageService;
//    //    private final FourCutRepository fourCutRepository;
//    private final CollectRepository collectRepository;
//
//    @Transactional
//    public GetPictureDto createPicture(CreatePictureDto createPictureDto, MultipartFile file, Long userId) {
//        //save image to s3, create picture object && save picture object to db
//        return savePicture(createPictureDto, file, 0, userId);
//    }
//
//    private GetPictureDto savePicture(CreatePictureDto createPictureDto, MultipartFile file, int idx, Long userId) {
//        try {
//            Picture savePicture = pictureRepository.save(
//                    Picture.create(ObjectStorageService.uploadFile(file, userId),
//                            createPictureDto.pictureIdx(),
//                            travelRepository.findById(createPictureDto.travelId())
//                                    .orElseThrow(() -> new IllegalArgumentException("travel not found")),
//                            userRepository.findById(userId)
//                                    .orElseThrow(() -> new IllegalArgumentException("user not found"))
//                            , createPictureDto.isFourCut(), idx
//                    )
//            );
//            return GetPictureDto.builder()
//                    .pictureId(savePicture.getId())
//                    .pictureIdx(savePicture.getPictureIdx())
//                    .pictureUrl(savePicture.getPictureUrl())
//                    .build();
//        } catch (IOException e) {
//            return null;
//        }
//    }
//
//    private void deleteS3AndDB(Long pictureId) {
//        try {
//            // remove file from s3
//            String[] split = pictureRepository.findById(pictureId)
//                    .orElseThrow(() -> new IllegalArgumentException("picture not found"))
//                    .getPictureUrl()
//                    .split("/");
//            ObjectStorageService.deleteFile(split[split.length - 1]);
//            // remove picture object from db
//            pictureRepository.deleteById(pictureId);
//
//        } catch (Exception e) {
//            throw new IllegalArgumentException("delete failed");
//        }
//    }
//
//    public List<GetPictureDto> getPictureList(Long travelId) {
//        List<GetPictureDto> pictureDtoList = new ArrayList<>();
//        pictureRepository.findAllByTravel_TravelIdAndIsDeletedFalse(travelId)  // add a condition to filter out deleted pictures
//                .forEach(picture ->
//                        pictureDtoList.add(
//                                GetPictureDto.builder()
//                                        .pictureId(picture.getId())
//                                        .pictureUrl(picture.getPictureUrl())
//                                        .pictureIdx(picture.getPictureIdx())
//                                        .build()
//                        ));
//        return pictureDtoList;
//    }
//
//    @Transactional
//    public void deleteAllByTravelId(Long travelId) {
//        pictureRepository.findAllByTravelId(travelId).forEach(picture -> {
//            deleteS3AndDB(picture.getId());
//        });
//    }
//
//    @Transactional
//    public GetFourCutDto createPictureFourCut(CreatePictureFourCutDto createPictureDto, List<MultipartFile> fileList, Long userId) {
//        //save image to s3, create picture object && save picture object to db
//        List<Long> pictureIdList = new ArrayList<>();
//        List<String> pictureUrlList = new ArrayList<>();
//        GetPictureDto getPictureDto;
//        for (int i = 0; i < fileList.size(); i++) {
//            getPictureDto = savePicture(CreatePictureDto.builder()
//                    .isFourCut(createPictureDto.isFourCut())
//                    .pictureIdx(createPictureDto.pictureIdx())
//                    .travelId(createPictureDto.travelId())
//                    .build(), fileList.get(i), i + 1, userId);
//            pictureIdList.add(getPictureDto.pictureId());
//            pictureUrlList.add(getPictureDto.pictureUrl());
//        }
//        return GetFourCutDto.builder()
//                .pictureId(pictureIdList)
//                .pictureUrl(pictureUrlList)
//                .pictureIdx(createPictureDto.pictureIdx())
//                .fourCutId(fourCutRepository.save(FourCut.create(
//                        createPictureDto.pictureIdx(),
//                        travelRepository.findById(createPictureDto.travelId())
//                                .orElseThrow(() -> new IllegalArgumentException("travel not found")),
//                        createPictureDto.collectId()
//                )).getId())
//                .collectId(createPictureDto.collectId())
//                .frameUrl(collectRepository.findById(createPictureDto.collectId())
//                        .orElseThrow(() -> new IllegalArgumentException("collect not found"))
//                        .getFrameUrl())
//                .build();
//    }
//
//    @Transactional
//    public boolean deletePicture(DeletePictureDto deletePictureDto) {
//        try {
//            pictureRepository.findAllByTravel_TravelIdAndPictureIdx(deletePictureDto.travelId(), deletePictureDto.pictureIdx())
//                    .forEach(picture -> {
//                        deleteS3AndDB(picture.getId());
//                    });
//            if (deletePictureDto.isFourCut()) {
////                fourCutRepository.deleteByTravel_TravelIdAndPictureIdx(deletePictureDto.travelId(), deletePictureDto.pictureIdx());
//            }
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
//
//    public List<GetFourCutDto> getFourCutList(Long travelId) {
//        List<GetFourCutDto> fourCutDtoList = new ArrayList<>();
//        fourCutRepository.findAllByTravel_TravelId(travelId).forEach(fourCut ->
//                fourCutDtoList.add(
//                        GetFourCutDto.builder()
//                                .fourCutId(fourCut.getId())
//                                .pictureIdx(fourCut.getPictureIdx())
//                                .collectId(fourCut.getCollectId())
//                                .frameUrl(collectRepository.findById(fourCut.getCollectId())
//                                        .orElseThrow(() -> new IllegalArgumentException("collect not found"))
//                                        .getFrameUrl())
//                                .build()
//                ));
//        return fourCutDtoList;
//    }
//}