package com.demoboletto.controller;

import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.UserProfileUpdateDto;
import com.demoboletto.dto.response.GetUserInfoDto;
import com.demoboletto.service.CollectService;
import com.demoboletto.service.UserService;
import com.demoboletto.type.EFrame;
import com.demoboletto.type.ESticker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 API")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final CollectService collectService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get User Info", description = "유저의 닉네임과 이름 정보를 가져오는 API")
    public ResponseEntity<GetUserInfoDto> getUserNameAndNickname(@PathVariable Long userId) {
        GetUserInfoDto userInfo = userService.getUserNameAndNickname(userId);
        return ResponseEntity.ok(userInfo);
    }

    @PatchMapping("")
    public ResponseEntity<ResponseDto<?>> updateUserProfile(@RequestBody @Validated UserProfileUpdateDto userProfileUpdateDto) {
        userService.updateUserProfile(userProfileUpdateDto);
        return ResponseEntity.ok(ResponseDto.ok("프로필이 성공적으로 업데이트되었습니다."));
    }

    @GetMapping("/{userId}/frames")
    @Operation(summary = "Get User collected Frames", description = "유저가 획득한 프레임과 개수를 가져오는 API")
    public ResponseEntity<?> getCollectedFrames(@PathVariable Long userId) {
        List<EFrame> frames = collectService.getCollectedFrames(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("frameCount", frames.size());
        response.put("frames", frames);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/stickers")
    @Operation(summary = "Get User collected Stickers", description = "유저가 획득한 스티커와 개수를 가져오는 API")
    public ResponseEntity<?> getCollectedStickers(@PathVariable Long userId) {
        List<ESticker> stickers = collectService.getCollectedStickers(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("stickerCount", stickers.size());
        response.put("stickers", stickers);
        return ResponseEntity.ok(response);
    }
}
