package com.demoboletto.dto.oauth.common;

import com.demoboletto.type.EProvider;

public interface OAuthUserInformation {
    EProvider getProvider();

    String getSerialId();

    String getProfileImgUrl();

    String getName();

    String getEmail();
}
