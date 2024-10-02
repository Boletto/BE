package com.demoboletto.controller;

import com.demoboletto.annotation.UserId;
import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.AppleLoginDto;
import com.demoboletto.dto.request.OauthLoginDto;
import com.demoboletto.dto.response.JwtTokenDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.service.AppleService;
import com.demoboletto.service.KakaoService;
import com.demoboletto.service.UserService;
import com.demoboletto.utility.HeaderUtil;
import com.demoboletto.constants.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name="OAuth", description = "인증 관련 API")
@RequestMapping("/api/v1")
public class OAuthController {

    private final AppleService appleService;
    private final KakaoService kakaoService;
    private final UserService userService;

    @PostMapping("/oauth/login")
    @Operation(summary = "소셜로그인", description = "클라이언트 사이드 인증을 통한 소셜 로그인")
    @Schema(name = "login", description = "소셜로그인")
    public ResponseDto<?> login(@RequestBody OauthLoginDto userloginDto) {
        return ResponseDto.ok(kakaoService.login(userloginDto));
    }

    @PostMapping("/oauth2/login/apple")
    @Operation(summary = "애플로그인", description = "애플 로그인")
    @Schema(name = "login", description = "애플 로그인")
    public ResponseDto<?> login(@RequestBody AppleLoginDto appleLoginDto) {
        return ResponseDto.ok(appleService.login(appleLoginDto.identityToken()));
    }

    @PostMapping("/auth/reissue")
    @Operation(summary = "Access 토큰 재발급", description = "Access 토큰을 재발급합니다.")
    public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response,
            @UserId Long userId) {
        String refreshToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.MISSING_REQUEST_HEADER));

        JwtTokenDto jwtTokenDto = kakaoService.reissue(userId, refreshToken);

        return ResponseDto.ok(jwtTokenDto);
    }

    @DeleteMapping("/auth/sign-out")
    @Operation(summary = "회원탈퇴", description = "현재 로그인된 사용자를 탈퇴 처리하고 DB에서 삭제합니다.")
    public ResponseDto<?> signout(@UserId Long userId) {
        System.out.println("User ID: " + userId);
        userService.deleteUser(userId);
        return ResponseDto.ok("회원 탈퇴가 완료되었습니다.");
    }

}
