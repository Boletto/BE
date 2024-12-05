package com.demoboletto.dto.oauth.common;

import com.demoboletto.type.EProvider;

public interface OAuthUserInformation {
    EProvider getProvider();

    String getProviderId();

    String getProfileImgUrl();

    String getNickname();

    String getEmail();
}
