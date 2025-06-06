package com.demoboletto.controller;

import com.demoboletto.annotation.UserId;
import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.oauth.*;
import com.demoboletto.dto.response.AppleLoginResponseDto;
import com.demoboletto.service.AppleService;
import com.demoboletto.service.AuthService;
import com.demoboletto.service.KakaoService;
import com.demoboletto.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@Tag(name = "OAuth", description = "인증 관련 API")
@RequestMapping("/api/v1")
public class OAuthController {

    private final AppleService appleService;
    private final KakaoService kakaoService;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/oauth/login")
    @Operation(summary = "소셜로그인", description = "클라이언트 사이드 인증을 통한 소셜 로그인")
    @Schema(name = "login", description = "소셜로그인")
    public ResponseDto<OAuthLoginResponseDto> login(@RequestBody KakaoUserInformation kakaoUserInformation) {
        return ResponseDto.ok(kakaoService.login(kakaoUserInformation));
    }

    @PostMapping("/oauth2/login/apple")
    @Operation(summary = "애플로그인", description = "애플 로그인")
    @Schema(name = "login", description = "애플 로그인")
    public ResponseDto<AppleLoginResponseDto> login(@RequestBody AppleLoginDto appleLoginDto) throws ParseException {
        return ResponseDto.ok(appleService.login(appleLoginDto));
    }

    @PostMapping("/auth/reissue")
    @Operation(summary = "Access 토큰 재발급", description = "Access 토큰을 재발급합니다.")
    public ResponseDto<JwtTokenDto> reissue(@RequestBody TokenRefreshDto tokenRefreshDto) {
        JwtTokenDto jwtTokenDto = authService.reissue(tokenRefreshDto.refreshToken());

        return ResponseDto.ok(jwtTokenDto);
    }

    @PostMapping("/auth/logout")
    @Operation(summary = "로그아웃", description = "로그아웃")
    public ResponseDto<String> logout(@Parameter(hidden = true) @UserId Long userId) {
        userService.logout(userId);
        return ResponseDto.ok("Logout Success");
    }

}
