package com.demoboletto.service;

import com.demoboletto.domain.Sticker;
import com.demoboletto.domain.Travel;
import com.demoboletto.dto.request.CreateStickerDto;
import com.demoboletto.dto.response.GetStickerDto;
import com.demoboletto.repository.StickerRepository;
import com.demoboletto.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StickerService {
    private final StickerRepository stickerRepository;
    private final TravelRepository travelRepository;

    public List<GetStickerDto> getStickerList(Long travelId) {
        List<GetStickerDto> stickerDtoList = new ArrayList<>();
        stickerRepository.findAllByTravelId(travelId).forEach(sticker ->
                stickerDtoList.add(
                        GetStickerDto.builder()
                                .stickerId(sticker.getStickerId())
                                .field(sticker.getField())
                                .locX(sticker.getLocX())
                                .locY(sticker.getLocY())
                                .scale(sticker.getScale())
                                .rotation(sticker.getRotation())
                                .build()
                ));
        return stickerDtoList;
    }
    @Transactional
    public boolean createSticker(List<CreateStickerDto> stickerDtoList, Long travelId) {
        try {
            stickerRepository.findAllByTravelId(travelId).forEach(stickerRepository::delete);
            if (stickerDtoList.size() == 0) {
                return true;
            }
            stickerRepository.saveAll(makeStickerList(stickerDtoList, travelId));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public List<Sticker> makeStickerList(List<CreateStickerDto> stickerDtoList, Long travelId) {
        List<Sticker> newStickerList = new ArrayList<>();
        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new IllegalArgumentException("travel not found"));
        for (int i = 0; i < stickerDtoList.size(); i++) {
            newStickerList.add(
                    Sticker.create(
                            stickerDtoList.get(i),
                            travel
                    )
            );
        }
        return newStickerList;
    }
    @Transactional
    public void deleteAllByTravelId(Long travelId) {
        stickerRepository.deleteAllByTravelId(travelId);
    }
}