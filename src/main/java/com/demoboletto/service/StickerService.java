package com.demoboletto.service;

import com.demoboletto.dto.response.GetPictureDto;
import com.demoboletto.dto.response.GetStickerDto;
import com.demoboletto.repository.PictureRepository;
import com.demoboletto.repository.StickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StickerService {
    private final StickerRepository stickerRepository;

    public List<GetStickerDto> getStickersByTravelId(Long travelId) {
        List<GetStickerDto> getStickerDtos = new ArrayList<>();
        stickerRepository.findAllByTravelId(travelId).forEach(sticker -> {
            getStickerDtos.add(GetStickerDto.builder()
                    .stickerId(sticker.getStickerId())
                    .travelId(sticker.getTravel().getTravelId())
                    .locX(sticker.getLocX())
                    .locY(sticker.getLocY())
                    .field(sticker.getField())
                    .build());
        });
        return getStickerDtos;
    }
}