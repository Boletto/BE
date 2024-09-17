package com.demoboletto.service;

import com.demoboletto.domain.Sticker;
import com.demoboletto.repository.StickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StickerService {
    private final StickerRepository stickerRepository;

    public List<Sticker> getStickerList(Long travelId) {
        return stickerRepository.findAllByTravelId(travelId);
    }
}