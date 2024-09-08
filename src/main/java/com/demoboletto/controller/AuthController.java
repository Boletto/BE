package com.demoboletto.controller;

import com.demoboletto.annotation.UserId;
import com.demoboletto.constants.Constants;
import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.OauthLoginDto;
import com.demoboletto.dto.response.JwtTokenDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.service.AuthService;
import com.demoboletto.utility.HeaderUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name="Auth", description = "인증 관련 API")
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/auth/email-duplicate")
    @Operation(summary = "이메일 중복 확인", description = "이메일 중복을 확인합니다.")
    public ResponseDto<?> checkDuplicate(
            @RequestParam(value = "email") String email
    ) {
        return ResponseDto.ok(authService.checkDuplicate(email));
    }

    @PostMapping("/oauth/login")
    @Operation(summary = "소셜로그인", description = "클라이언트 사이드 인증을 통한 소셜 로그인")
    @Schema(name = "login", description = "로그인")
    public ResponseDto<?> login(@RequestBody OauthLoginDto userloginDto) {
        return ResponseDto.ok(authService.login(userloginDto));
    }


}
