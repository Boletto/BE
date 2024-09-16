package com.demoboletto.controller;

import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.CreatePictureDto;
import com.demoboletto.service.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name="Picture", description = "사진 관련 API")
@RequestMapping("/api/v1/travel/photo")
public class PictureController {
    private final PictureService pictureService;
    @GetMapping("/get")
    @Operation(summary = "get picture list", description = "Get all picture list in the travel.")
    public ResponseDto<?> getPictureList(@RequestParam(value = "travel_id") Long id) {
        return ResponseDto.ok(pictureService.getPicturesByTravelId(id));
    }
    @PostMapping("/create")
    @Operation(summary = "save picture", description = "save picture in the travel.")
    public ResponseDto<?> createPictureList(@RequestBody CreatePictureDto pictureDto) {
        return pictureService.createPicture(pictureDto) ? ResponseDto.ok("success") : ResponseDto.fail("fail");
    }
}
