package com.demoboletto.service;

import com.demoboletto.domain.Collect;
import com.demoboletto.repository.CollectRepository;
import com.demoboletto.type.EFrame;
import com.demoboletto.type.ESticker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollectService {

    private final CollectRepository collectRepository;

    public List<EFrame> getCollectedFrames(Long userId) {
        List<Collect> collections = collectRepository.findByUserIdAndFrameTypeIsNotNull(userId);
        return collections.stream()
                .map(Collect::getFrameType)
                .collect(Collectors.toList());
    }

    // 스티커 조회
    public List<ESticker> getCollectedStickers(Long userId) {
        List<Collect> collections = collectRepository.findByUserIdAndStickerTypeIsNotNull(userId);
        return collections.stream()
                .map(Collect::getStickerType)
                .collect(Collectors.toList());
    }
}
