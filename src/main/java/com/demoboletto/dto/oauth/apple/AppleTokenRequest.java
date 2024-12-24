package com.demoboletto.dto.oauth.apple;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppleTokenRequest {
    private String clientId;
    private String clientSecret;
    private String code;
    private String grantType;
    private String redirectUri;
}