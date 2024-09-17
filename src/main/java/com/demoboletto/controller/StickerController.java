package com.demoboletto.controller;

import com.demoboletto.service.StickerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name="Sticker", description = "스티커 관련 API")
@RequestMapping("/api/v1/travel/memory/sticker")
public class StickerController {
    private final StickerService stickerService;

}
