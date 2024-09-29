package com.demoboletto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "OAuth Login Response Dto")
public record OAuthLoginResponseDto(
        @JsonProperty("accessToken")
        @Schema(description = "액세스 토큰")
        String accessToken,

        @JsonProperty("refreshToken")
        @Schema(description = "리프레시 토큰")
        String refreshToken,

        @JsonProperty("userId")
        @Schema(description = "유저의 기본키", example = "2")
        Long userId,

        @JsonProperty("userName")
        @Schema(description = "유저의 이름", example = "홍길동")
        String name,

        @JsonProperty("userNickName")
        @Schema(description = "유저의 닉네임", example = "길동이")
        String nickname,

        @JsonProperty("userProfile")
        @Schema(description = "유저의 프로필 이미지 URL", example = "https://example.com/profile.jpg")
        String userProfile
) {
}