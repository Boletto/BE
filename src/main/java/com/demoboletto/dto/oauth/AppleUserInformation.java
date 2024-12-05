package com.demoboletto.dto.oauth;

import com.demoboletto.dto.oauth.common.OAuthUserInformation;
import com.demoboletto.type.EProvider;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record AppleUserInformation(
        @NotNull
        @JsonProperty("providerId")
        @Schema(description = "Apple에서 제공한 id", example = "abc123")
        String providerId,

        @NotNull
        @JsonProperty("email")
        @Schema(description = "애플에서 제공한 이메일", example = "user@example.com")
        String email
) implements OAuthUserInformation {

    @Override
    public EProvider getProvider() {
        return EProvider.APPLE;  // Apple 로그인 정보이므로 항상 APPLE 반환
    }

    @Override
    public String getSerialId() {
        return providerId;  // record에서 자동 생성된 providerId() 메서드를 호출
    }

    @Override
    public String getProfileImgUrl() {
        return null;  // Apple 로그인에는 프로필 이미지가 없는 경우를 처리
    }

    @Override
    public String getNickname() {
        return null;  // Apple 로그인에 닉네임이 없을 경우 null 반환
    }

    @Override
    public String getEmail() {
        return email;  // record에서 자동 생성된 email() 메서드를 호출
    }
}