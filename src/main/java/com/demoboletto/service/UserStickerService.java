package com.demoboletto.service;

import com.demoboletto.domain.SysFrame;
import com.demoboletto.domain.SysSticker;
import com.demoboletto.domain.UserFrame;
import com.demoboletto.domain.UserSticker;
import com.demoboletto.dto.response.GetUserUsableFrameDto;
import com.demoboletto.dto.response.GetUserUsableStickerDto;
import com.demoboletto.repository.SysStickerRepository;
import com.demoboletto.repository.UserStickerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserStickerService {
    private final UserStickerRepository userStickerRepository;
    private final SysStickerRepository sysStickerRepository;

    public List<GetUserUsableStickerDto> getUsableStickers(Long userId) {
        List<SysSticker> defaultStickers = sysStickerRepository.findDefaultStickers();

        // Retrieve user-specific stickers
        List<UserSticker> userStickers = userStickerRepository.findAllByUserId(userId);

        // Map default stickers to DTOs with `isOwned=false`
        Map<Long, GetUserUsableStickerDto> frameMap = defaultStickers.stream()
                .collect(Collectors.toMap(
                        SysSticker::getStickerId,
                        GetUserUsableStickerDto::of
                ));

        // Override with user-owned stickers (`isOwned=true`)
        for (UserSticker userSticker : userStickers) {
            frameMap.put(userSticker.getSticker().getStickerId(), GetUserUsableStickerDto.of(userSticker));
        }

        // Return as a list
        return new ArrayList<>(frameMap.values());
    }
}
