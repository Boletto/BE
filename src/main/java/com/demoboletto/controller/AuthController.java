package com.demoboletto.controller;

import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.AuthSignUpDto;
import com.demoboletto.dto.request.OauthLoginDto;
import com.demoboletto.dto.response.JwtTokenDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.service.AppleService;
import com.demoboletto.service.AuthService;
import com.demoboletto.utility.HeaderUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name="Auth", description = "인증 관련 API")
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;
    private final AppleService appleService;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    // Apple 로그인 요청을 리디렉션
    @GetMapping("/oauth2/login/apple")
    public void loginRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        redirectStrategy.sendRedirect(request, response, appleService.getAppleLoginUrl());
    }

    @PostMapping("/oauth/login")
    @Operation(summary = "소셜로그인", description = "클라이언트 사이드 인증을 통한 소셜 로그인")
    @Schema(name = "login", description = "로그인")
    public ResponseDto<?> login(@RequestBody OauthLoginDto userloginDto) {
        return ResponseDto.ok(authService.login(userloginDto));
    }

    @PostMapping("/callback/apple")
    @Operation(summary = "callback", description = "로그인 성공 . authorization code를 service 단에 넘겨줌")
    public ResponseDto<?> callback(HttpServletRequest request, HttpServletResponse response) {

        // 애플 회원가입 또는 로그인 실패
        if (appleService.loginwithApple(request.getParameter("code"), response) == null) {
            return ResponseDto.fail(ErrorCode.FAILURE_LOGIN);
        }

        return ResponseDto.ok("Apple Login Success");
    }

}
