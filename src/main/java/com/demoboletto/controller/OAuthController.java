package com.demoboletto.controller;

import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.AppleLoginDto;
import com.demoboletto.dto.request.OauthLoginDto;
import com.demoboletto.service.AppleService;
import com.demoboletto.service.KakaoService;
import com.demoboletto.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name="OAuth", description = "인증 관련 API")
@RequestMapping("/api/v1")
public class OAuthController {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final UserService userService;
    private final AppleService appleService;
    private final KakaoService kakaoService;

    @PostMapping("/oauth/login")
    @Operation(summary = "소셜로그인", description = "클라이언트 사이드 인증을 통한 소셜 로그인")
    @Schema(name = "login", description = "소셜로그인")
    public ResponseDto<?> login(@RequestBody OauthLoginDto userloginDto) {
        return ResponseDto.ok(kakaoService.login(userloginDto));
    }

    @PostMapping("/oauth/login/apple")
    @Operation(summary = "애플로그인", description = "애플 로그인")
    @Schema(name = "login", description = "애플 로그인")
    public ResponseDto<?> login(@RequestBody AppleLoginDto appleLoginDto) {
        return ResponseDto.ok(appleService.login(appleLoginDto.accessToken()));
    }

}
