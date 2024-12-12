package com.demoboletto.service;

import com.demoboletto.domain.SysSticker;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserSticker;
import com.demoboletto.dto.response.GetUserUsableStickerDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.SysStickerRepository;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.repository.UserStickerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final UserRepository userRepository;

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

    public void saveUserSticker(Long userId, String stickerCode) {
        SysSticker sysSticker = sysStickerRepository.findByStickerCode(stickerCode)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SYS_STICKER));
        if (sysSticker.isEventExpired()) {
            throw new CommonException(ErrorCode.EVENT_EXPIRED);
        }

        User user = getUser(userId);
        UserSticker userSticker = UserSticker.builder()
                .sticker(sysSticker)
                .user(user)
                .build();
        try {
            userStickerRepository.save(userSticker);
        } catch (DataIntegrityViolationException e) {
            throw new CommonException(ErrorCode.ALREADY_COLLECTED_STICKER);
        }
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
    }
}
