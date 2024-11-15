package com.demoboletto.service;

import com.demoboletto.domain.Sticker;
import com.demoboletto.domain.Travel;
import com.demoboletto.dto.request.CreateStickerDto;
import com.demoboletto.dto.response.GetStickerDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.StickerRepository;
import com.demoboletto.repository.travel.TravelRepository;
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
    public void createSticker(List<CreateStickerDto> stickerDtoList, Long travelId) {
        stickerRepository.deleteAllByTravelId(travelId);
        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TRAVEL));

        List<Sticker> stickers = stickerDtoList.stream()
                .map(createStickerDto -> Sticker.create(createStickerDto, travel))
                .toList();
        stickerRepository.saveAll(stickers);
    }

    @Transactional
    public void deleteAllByTravelId(Long travelId) {
        stickerRepository.deleteAllByTravelId(travelId);
    }
}