package com.demoboletto.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "UserProfileUpdateDto", description = "request for updating user profile")
public record UserProfileUpdateDto(
        @JsonProperty("nickName")
        @Schema(description = "유저 닉네임", example = "닉네임쓰")
        String nickName,

        @JsonProperty("name")
        @Schema(description = "유저 이름", example = "홍길동")
        String name,

        @JsonProperty("profile_default")
        @Schema(description = "프로필 기본 여부", example = "true")
        boolean profileDefault
) {
}
