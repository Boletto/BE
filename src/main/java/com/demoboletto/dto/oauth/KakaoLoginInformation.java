package com.demoboletto.dto.oauth;

import com.demoboletto.dto.oauth.common.OAuthUserInformation;
import com.demoboletto.type.EProvider;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(name = "OauthLoginDto", description = "소셜 로그인 요청")
public record KakaoLoginInformation(
        @NotNull(message = "providerId는 null 값이 될 수 없습니다.")
        @JsonProperty("serialId") @Schema(description = "시리얼 아이디로 삼을 수 있는 사용자 ID를 담는다.", example = "203912941")
        String serialId,

        @NotNull(message = "provider는 null 값이 될 수 없습니다.")
        @JsonProperty("provider") @Schema(description = "프로바이더(소셜로그인 제공자)", example = "KAKAO")
        EProvider provider,

        @JsonProperty("nickname") @Schema(description = "유저의 닉네임", example = "leedaeun")
        String nickname
) implements OAuthUserInformation {
    @Override
    public EProvider getProvider() {
        return EProvider.KAKAO;
    }

    @Override
    public String getProviderId() {
        return serialId;
    }

    @Override
    public String getProfileImgUrl() {
        return null;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public String getEmail() {
        return null;
    }
}