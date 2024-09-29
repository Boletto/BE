package com.demoboletto.controller;

import com.demoboletto.annotation.UserId;
import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.CreatePictureDto;
import com.demoboletto.dto.request.CreatePictureFourCutDto;
import com.demoboletto.dto.request.DeletePictureDto;
import com.demoboletto.dto.response.GetPictureDto;
import com.demoboletto.service.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name="Picture", description = "사진 관련 API")
@RequestMapping("/api/v1/travel/memory/picture")
public class PictureController {
    private final PictureService pictureService;

    @PostMapping("/save")
    @Operation(summary = "save picture", description = "save picture in the travel.")
    public ResponseDto<?> createPictureList(@RequestPart(value = "data") CreatePictureDto pictureDto, @RequestPart(required = false, value = "picture_file") MultipartFile picture_file, @UserId Long userId) {
        return ResponseDto.created(pictureService.createPicture(pictureDto, picture_file, userId));
    }
    @DeleteMapping("/delete")
    @Operation(summary = "delete picture", description = "delete picture in the travel.")
    public ResponseDto<?> deletePictureList(@RequestBody DeletePictureDto deletePictureDto) {
        return pictureService.deletePicture(deletePictureDto) ? ResponseDto.ok("success") : ResponseDto.fail("fail");
    }
    @PostMapping("/save/fourCut")
    @Operation(summary = "save fourCut picture", description = "save fourCut picture in the travel.")
    public ResponseDto<?> createPictureFourCutList(@RequestPart(value = "data") CreatePictureFourCutDto pictureDto, @RequestPart(required = false, value = "picture_file") List<MultipartFile> picture_file,@UserId Long userId) {
        return ResponseDto.created(pictureService.createPictureFourCut(pictureDto, picture_file, userId));
    }
}
