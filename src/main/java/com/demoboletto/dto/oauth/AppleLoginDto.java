package com.demoboletto.dto.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record AppleLoginDto(
        @NotBlank(message = "identity token은 필수 입력입니다.")
        @JsonProperty("identity_token")
        String identityToken,

        @JsonProperty("user_name")
        String userName
//
//        @JsonProperty("device_token")
//        String deviceToken
) {
}
