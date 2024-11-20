package com.demoboletto.service;

import com.demoboletto.domain.SysSticker;
import com.demoboletto.dto.request.CreateSysStickerDto;
import com.demoboletto.dto.response.GetStickerInfoDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.SysStickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemService {
    private final SysStickerRepository sysStickerRepository;
    private final ObjectStorageService objectStorageService;
    private final String STICKER_PATH = "stickers";

    public List<GetStickerInfoDto> getSystemStickers() {
        List<SysSticker> stickers = sysStickerRepository.findAll();
        return stickers.stream().map(GetStickerInfoDto::of).toList();
    }


    public void saveSticker(CreateSysStickerDto createSysStickerDto) {
        String stickerUrl;
        try {
            stickerUrl = objectStorageService.uploadSystemFile(createSysStickerDto.getFile(), STICKER_PATH);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }
        SysSticker sysSticker = SysSticker.builder()
                .stickerName(createSysStickerDto.getStickerName())
                .stickerType(createSysStickerDto.getStickerType())
                .defaultProvided(createSysStickerDto.isDefaultProvided())
                .stickerUrl(stickerUrl)
                .description(createSysStickerDto.getDescription())
                .build();
        sysStickerRepository.save(sysSticker);
    }
}
