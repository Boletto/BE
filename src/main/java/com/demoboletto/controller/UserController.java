package com.demoboletto.controller;

import com.demoboletto.annotation.UserId;
import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.UserProfileUpdateDto;
import com.demoboletto.dto.response.GetUserInfoDto;
import com.demoboletto.dto.response.GetUserProfileUpdateDto;
import com.demoboletto.dto.response.GetUserUsableFrameDto;
import com.demoboletto.dto.response.GetUserUsableStickerDto;
import com.demoboletto.service.UserFrameService;
import com.demoboletto.service.UserService;
import com.demoboletto.service.UserStickerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 API")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserFrameService userFrameService;
    private final UserStickerService userStickerService;

    @GetMapping("")
    @Operation(summary = "Get User Info", description = "유저의 닉네임과 이름 정보를 가져오는 API")
    public ResponseDto<GetUserInfoDto> getUserNameAndNickname(@Parameter(hidden = true) @UserId Long userId) {
        GetUserInfoDto userInfo = userService.getUserNameAndNickname(userId);
        return ResponseDto.ok(userInfo);
    }


    @PatchMapping("")
    public ResponseDto<GetUserProfileUpdateDto> updateUserProfile(@Parameter(hidden = true) @UserId Long userId, @RequestPart(value = "data") @Validated UserProfileUpdateDto userProfileUpdateDto, @RequestPart(value = "file", required = false) MultipartFile userProfile) {
        GetUserProfileUpdateDto getuserProfileUpdateDto = userService.updateUserProfile(userId, userProfileUpdateDto, userProfile);
        return ResponseDto.ok(getuserProfileUpdateDto);
    }

    @GetMapping("/frames")
    @Operation(summary = "Get User usable frames", description = "유저가 사용할 수 있는 프레임 List")
    public ResponseDto<List<GetUserUsableFrameDto>> getUsableFrames(@Parameter(hidden = true) @UserId Long userId) {
        return ResponseDto.ok(userFrameService.getUsableFrames(userId));
    }

    @PostMapping("/frames/{frameCode}")
    @Operation(summary = "Post user usable Frames", description = "frame 정보를 받아 유저가 사용할 수 있는 프레임을 저장합니다.")
    public ResponseDto<?> saveUserFrame(@Parameter(hidden = true) @UserId Long userId, @PathVariable String frameCode) {
        userFrameService.saveUserFrame(userId, frameCode);
        return ResponseDto.ok("Success");
    }

    @PostMapping(value = "/frames", consumes = "multipart/form-data")
    @Operation(summary = "Create User Custom Frame", description = "유저가 사용할 수 있는 커스텀 프레임을 추가합니다.")
    public ResponseDto<GetUserUsableFrameDto> createUserCustomFrame(@Parameter(hidden = true) @UserId Long userId,
                                                                    @RequestPart MultipartFile file) {
        return ResponseDto.created(userFrameService.createUserCustomFrame(userId, file));
    }

    @GetMapping("/stickers")
    @Operation(summary = "Get User usable Stickers", description = "유저가 사용할 수 있는 스티커 List")
    public ResponseDto<List<GetUserUsableStickerDto>> getUsableStickers(@Parameter(hidden = true) @UserId Long userId) {
        return ResponseDto.ok(userStickerService.getUsableStickers(userId));
    }

    @PostMapping("/stickers/{stickerCode}")
    public ResponseDto<?> saveUserSticker(@Parameter(hidden = true) @UserId Long userId, @PathVariable String stickerCode) {
        userStickerService.saveUserSticker(userId, stickerCode);
        return ResponseDto.ok("Success");
    }


    @PutMapping("/device-token")
    public ResponseDto<?> updateNotificationToken(@Parameter(hidden = true) @UserId Long userId, @RequestParam String token) {
        userService.updateDeviceToken(userId, token);
        return ResponseDto.ok("유저의 푸시 알림 토큰이 업데이트 되었습니다.");
    }

}
