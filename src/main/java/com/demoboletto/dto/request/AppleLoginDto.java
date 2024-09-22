package com.demoboletto.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AppleLoginDto(
        @NotBlank(message = "access token은 필수 입력입니다.")
        String accessToken
) {
}
