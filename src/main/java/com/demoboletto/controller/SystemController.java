package com.demoboletto.controller;

import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.CreateSysStickerDto;
import com.demoboletto.dto.response.GetStickerInfoDto;
import com.demoboletto.service.SystemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "System Query", description = "Sticker, frame 등에 대한 Metadata 조회")
@RequestMapping("/api/v1/sys")
public class SystemController {
    private final SystemService systemService;

    @GetMapping("/stickers")
    public ResponseDto<List<GetStickerInfoDto>> getStickers() {
        return ResponseDto.ok(systemService.getSystemStickers());
    }

    @PostMapping("/stickers")
    public ResponseDto<?> createSticker(@ModelAttribute CreateSysStickerDto createSysStickerDto) {
        systemService.saveSticker(createSysStickerDto);
        return ResponseDto.ok("Create Sticker Success");
    }

}
