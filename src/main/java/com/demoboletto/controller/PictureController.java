package com.demoboletto.controller;

import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.CreatePictureDto;
import com.demoboletto.service.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name="Picture", description = "사진 관련 API")
@RequestMapping("/api/v1/travel/memory/picture")
public class PictureController {
    private final PictureService pictureService;

    @PostMapping("/save")
    @Operation(summary = "save picture", description = "save picture in the travel.")
    public ResponseDto<?> createPictureList(@RequestPart(value = "data") CreatePictureDto pictureDto, @RequestPart(required = false, value = "picture_file") MultipartFile picture_file) {
        return pictureService.createPicture(pictureDto,picture_file) ? ResponseDto.ok("success") : ResponseDto.fail("fail");
    }
    @DeleteMapping("/delete")
    @Operation(summary = "delete picture", description = "delete picture in the travel.")
    public ResponseDto<?> deletePictureList(@RequestParam(value = "picture_id") Long pictureId) {
        return pictureService.deletePicture(pictureId) ? ResponseDto.ok("success") : ResponseDto.fail("fail");
    }
}
