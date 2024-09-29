package com.demoboletto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Apple Login Response Dto")
public record AppleLoginResponseDto(
        @JsonProperty("accessToken")
        @Schema(description = "액세스 토큰")
        String accessToken,

        @JsonProperty("refreshToken")
        @Schema(description = "리프레시 토큰")
        String refreshToken,

        @JsonProperty("userId")
        @Schema(description = "유저의 기본키", example="2")
        Long userId,

        @JsonProperty("userName")
        @Schema(description = "유저의 이름", example = "이다은")
        String name,

        @JsonProperty("userNickName")
        @Schema(description = "유저의 닉네임", example = "leeeunda")
        String nickname,

        @JsonProperty("userProfile")
        @Schema(description = "유저의 프로필 이미지 url", example = "https://boletto.s3.ap-northeast-2.amazonaws.com/스크린샷 2023-12-12 오후 12.49.20.png")
        String userProfile
) {
}