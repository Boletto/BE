package com.demoboletto.factory;

import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.type.EProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OAuthServiceFactory {
    private final Map<EProvider, OAuthService> oAuthServices;

    @Autowired
    public OAuthServiceFactory(List<OAuthService> oAuthServiceList) {
        oAuthServices = oAuthServiceList.stream()
                .collect(Collectors.toMap(OAuthService::getProvider, Function.identity()));
    }

    public OAuthService getOAuthService(EProvider provider) {
        OAuthService service = oAuthServices.get(provider);
        if (service == null) {
            throw new CommonException(ErrorCode.INVALID_PROVIDER);
        }
        return service;
    }
}