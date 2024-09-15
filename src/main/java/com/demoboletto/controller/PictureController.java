package com.demoboletto.controller;

import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.service.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name="Picture", description = "사진 관련 API")
@RequestMapping("/api/v1/travel/photo")
public class PictureController {
    private final PictureService pictureService;
    @GetMapping("/")
    @Operation(summary = "get picture list", description = "Get all picture list in the travel.")
    public ResponseDto<?> getPictureList(@RequestParam(value = "travel_id") Long id) {
        return ResponseDto.ok(pictureService.getPicturesByTravelId(id));
    }
}
