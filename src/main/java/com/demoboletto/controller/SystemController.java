package com.demoboletto.controller;

import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.CreateSysFrameDto;
import com.demoboletto.dto.request.CreateSysStickerDto;
import com.demoboletto.dto.response.GetSysFrameInfoDto;
import com.demoboletto.dto.response.GetSysStickerInfoDto;
import com.demoboletto.service.SystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "System", description = "Sticker, frame 등에 대한 Metadata 조회")
@RequestMapping("/api/v1/sys")
public class SystemController {
    private final SystemService systemService;

    @GetMapping("/stickers")
    @Operation(summary = "스티커 목록 조회", description = "시스템에서 제공하는 스티커 목록을 조회합니다.")
    public ResponseDto<List<GetSysStickerInfoDto>> getStickers(
            @Parameter(description = "이벤트 여부", example = "true")
            @RequestParam(required = false) boolean isEvent) {
        if (isEvent) {
            return ResponseDto.ok(systemService.getEventStickers());
        }
        return ResponseDto.ok(systemService.getSystemStickers());
    }

    @PostMapping(value = "/stickers", consumes = "multipart/form-data")
    @Operation(summary = "스티커 생성", description = "시스템에서 제공하는 스티커를 생성합니다.")
    public ResponseDto<?> createSticker(
            @RequestPart CreateSysStickerDto createSysStickerDto,
            @RequestPart MultipartFile file
    ) {
        systemService.saveSticker(createSysStickerDto, file);
        return ResponseDto.created("Create Sticker Success");
    }

    @GetMapping("/frames")
    @Operation(summary = "프레임 목록 조회", description = "시스템에서 제공하는 프레임 목록을 조회합니다.")
    public ResponseDto<List<GetSysFrameInfoDto>> getFrames(
            @Parameter(description = "이벤트 여부", example = "true")
            @RequestParam(required = false) boolean isEvent) {
        if (isEvent) {
            return ResponseDto.ok(systemService.getEventFrames());
        }
        return ResponseDto.ok(systemService.getSystemFrames());
    }

    @PostMapping(value = "/frames", consumes = "multipart/form-data")
    @Operation(summary = "프레임 생성", description = "시스템에서 제공하는 프레임을 생성합니다.")
    public ResponseDto<?> createFrame(
            @RequestPart CreateSysFrameDto createSysFrameDto,
            @RequestPart MultipartFile file
    ) {
        systemService.saveFrame(createSysFrameDto, file);
        return ResponseDto.created("Create Frame Success");
    }

}
