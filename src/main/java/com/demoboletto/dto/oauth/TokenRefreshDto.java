package com.demoboletto.dto.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "TokenRefreshDto", description = "토큰 재발급 요청")
public record TokenRefreshDto(
        @NotNull
        @JsonProperty("refresh_token")
        String refreshToken) {
}
