package com.demoboletto.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name="Sticker", description = "스티커 관련 API")
@RequestMapping("/api/v1")
public class StickerController {
}
