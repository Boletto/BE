package com.demoboletto.service;

import com.demoboletto.domain.SysSticker;
import com.demoboletto.domain.Travel;
import com.demoboletto.domain.TravelSticker;
import com.demoboletto.dto.request.UpdateTravelMemoryStickerDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.SysStickerRepository;
import com.demoboletto.repository.travel.TravelRepository;
import com.demoboletto.repository.travel.TravelStickerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelStickerService {
    private final TravelRepository travelRepository;
    private final TravelStickerRepository travelStickerRepository;
    private final SysStickerRepository sysStickerRepository;

    @Transactional
    public void updateTravelMemoryStickers(Long userId, Long travelId, List<UpdateTravelMemoryStickerDto> stickers) {
        Travel travel = travelRepository.findByUserIdAndTravelId(userId, travelId)
                .orElseThrow(() -> new CommonException(ErrorCode.ACCESS_DENIED));
        System.out.println("break point");
        List<TravelSticker> prevStickers = travelStickerRepository.findAllByTravel(travel);
        travelStickerRepository.deleteAll(prevStickers);

        List<TravelSticker> newStickers = new ArrayList<>();
        stickers.forEach(sticker -> {
            SysSticker sysSticker = sysStickerRepository.findByStickerCode(sticker.getStickerCode())
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SYS_STICKER));
            TravelSticker travelSticker = TravelSticker.builder()
                    .sysSticker(sysSticker)
                    .locX(sticker.getLocX())
                    .locY(sticker.getLocY())
                    .travel(travel)
                    .rotation(sticker.getRotation())
                    .scale(sticker.getScale())
                    .content(sticker.getContent())
                    .build();
            newStickers.add(travelSticker);
        });

        travelStickerRepository.saveAll(newStickers);
    }
}
