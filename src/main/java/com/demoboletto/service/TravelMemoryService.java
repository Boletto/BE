package com.demoboletto.service;

import com.demoboletto.domain.*;
import com.demoboletto.dto.temp.GetTravelMemoryDto;
import com.demoboletto.dto.temp.UpdateTravelEachMemoryDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.SysFrameRepository;
import com.demoboletto.repository.TravelMemoryRepository;
import com.demoboletto.repository.TravelStickerRepository;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.repository.travel.TravelRepository;
import com.demoboletto.repository.travel.UserTravelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelMemoryService {

    private final TravelMemoryRepository travelMemoryRepository;
    private final ObjectStorageService objectStorageService;
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final SysFrameRepository sysFrameRepository;
    private final UserTravelRepository userTravelRepository;
    private final TravelStickerRepository travelStickerRepository;

    @Transactional
    public void createTravelEachMemory(Long userId, Long travelId, Long memoryIdx, UpdateTravelEachMemoryDto updateTravelEachMemoryDto, List<MultipartFile> files) {
        System.out.println("breakpoint");
        Travel travel = userTravelRepository.findByUserIdAndTravelId(userId, travelId)
                .orElseThrow(() -> new CommonException(ErrorCode.ACCESS_DENIED));
        SysFrame sysFrame = sysFrameRepository.findByFrameCode(updateTravelEachMemoryDto.getFrameCode())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SYS_FRAME));
        User user = getUser(userId);
        // TODO: Frame 소유 여부 핸들링 필요?

        TravelMemory travelMemory = travelMemoryRepository.findByTravelAndMemoryIdx(travel, memoryIdx)
                .orElse(TravelMemory.builder()
                        .travel(travel)
                        .memoryIdx(memoryIdx)
                        .frame(sysFrame)
                        .memoryType(updateTravelEachMemoryDto.getMemoryType())
                        .build()
                );


        List<String> pictureUrls;
//        upload pictures
        try {
            pictureUrls = objectStorageService.uploadFileList(files, userId);
        } catch (IOException e) {
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }

        List<Picture> pictures = new ArrayList<>();
        for (int i = 0; i < pictureUrls.size(); i++) {
            Picture picture = Picture.builder()
                    .pictureIdx(i)
                    .user(user)
                    .pictureUrl(pictureUrls.get(i))
                    .build();
            pictures.add(picture);
        }

        travelMemory.attachPictures(pictures);
        travelMemoryRepository.save(travelMemory);
    }

    @Transactional
    public void deleteTravelEachMemory(Long userId, Long travelId, Long memoryIdx) {
        Travel travel = userTravelRepository.findByUserIdAndTravelId(userId, travelId)
                .orElseThrow(() -> new CommonException(ErrorCode.ACCESS_DENIED));
        TravelMemory travelMemory = travelMemoryRepository.findByTravelAndMemoryIdx(travel, memoryIdx)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MEMORY));

        travelMemory.detachPictures();
        travelMemoryRepository.delete(travelMemory);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }

    public GetTravelMemoryDto getTravelMemory(Long userId, Long travelId) {
        Travel travel = userTravelRepository.findByUserIdAndTravelId(userId, travelId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TRAVEL));
        List<TravelMemory> travelMemories = travelMemoryRepository.findAllByTravel(travel);
        List<TravelSticker> travelStickers = travelStickerRepository.findAllByTravel(travel);

        return GetTravelMemoryDto.builder()
                .memories(travelMemories)
                .stickers(travelStickers)
                .build();
    }
}