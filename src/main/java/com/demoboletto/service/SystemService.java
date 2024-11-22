package com.demoboletto.service;

import com.demoboletto.domain.SysFrame;
import com.demoboletto.domain.SysSticker;
import com.demoboletto.dto.request.CreateSysFrameDto;
import com.demoboletto.dto.request.CreateSysStickerDto;
import com.demoboletto.dto.response.GetSysFrameInfoDto;
import com.demoboletto.dto.response.GetSysStickerInfoDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.SysFrameRepository;
import com.demoboletto.repository.SysStickerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemService {
    private final SysStickerRepository sysStickerRepository;
    private final SysFrameRepository sysFrameRepository;
    private final ObjectStorageService objectStorageService;
    private final String STICKER_PATH = "stickers";
    private final String FRAME_PATH = "frames";

    public List<GetSysStickerInfoDto> getSystemStickers() {
        List<SysSticker> stickers = sysStickerRepository.findAll();
        return stickers.stream().map(GetSysStickerInfoDto::of).toList();
    }


    public void saveSticker(CreateSysStickerDto createSysStickerDto, MultipartFile file) {
        String stickerUrl;
        try {
            stickerUrl = objectStorageService.uploadSystemFile(file, STICKER_PATH);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }
        SysSticker sysSticker = SysSticker.builder()
                .stickerName(createSysStickerDto.getStickerName())
                .stickerCode(createSysStickerDto.getStickerCode())
                .stickerType(createSysStickerDto.getStickerType())
                .defaultProvided(createSysStickerDto.isDefaultProvided())
                .stickerUrl(stickerUrl)
                .description(createSysStickerDto.getDescription())
                .build();
        try {
            sysStickerRepository.save(sysSticker);
        } catch (DataIntegrityViolationException e) {
            throw new CommonException(ErrorCode.DUPLICATED_SYS_STICKER_CODE);
        }
    }

    public List<GetSysFrameInfoDto> getSystemFrames() {
        return sysFrameRepository.findAll().stream().map(GetSysFrameInfoDto::of).toList();
    }

    public void saveFrame(CreateSysFrameDto createSysFrameDto, MultipartFile file) {
        String frameUrl;
        try {
            frameUrl = objectStorageService.uploadSystemFile(file, FRAME_PATH);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }
        SysFrame sysFrame = SysFrame.builder()
                .frameName(createSysFrameDto.getFrameName())
                .frameCode(createSysFrameDto.getFrameCode())
                .frameUrl(frameUrl)
                .description(createSysFrameDto.getDescription())
                .build();
        try {
            sysFrameRepository.save(sysFrame);
        } catch (DataIntegrityViolationException e) {
            throw new CommonException(ErrorCode.DUPLICATED_SYS_FRAME_CODE);
        }
    }
}
