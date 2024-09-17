package com.demoboletto.service;

import com.demoboletto.domain.Travel;
import com.demoboletto.dto.request.MemoryEditDto;
import com.demoboletto.dto.response.GetMemoryDto;
import com.demoboletto.repository.*;
import com.demoboletto.type.EStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoryService {
    private final PictureService pictureService;
    private final StickerService stickerService;
    private final SpeechService speechService;
    private final TravelRepository travelRepository;

    public GetMemoryDto getMemoryByTravelId(Long travelId) {

        // get picture data from db & get sticker data from db & get speech data from db
        GetMemoryDto getMemoryDto = GetMemoryDto.builder()
                .travelId(travelId)
                .pictures(pictureService.getPictureList(travelId))
                .stickers(stickerService.getStickerList(travelId))
                .speeches(speechService.getSpeechList(travelId))
                .build();
        return getMemoryDto;
    }
    @Transactional
    public boolean memoryEditMode(MemoryEditDto pictureDto) {
        Travel travel = travelRepository.findById(pictureDto.travelId())
                .orElseThrow(() -> new IllegalArgumentException("travel not found"));
        switch (pictureDto.status()) {
            case LOCK:
                if (travel.getStatus() == EStatusType.LOCK) {
                    return false;
                }
                travel.setStatus(EStatusType.LOCK);
                travelRepository.save(travel);
                return true;
            case UNLOCK:
                travel.setStatus(EStatusType.UNLOCK);
                travelRepository.save(travel);
                return true;
        }
        return false;
    }
}