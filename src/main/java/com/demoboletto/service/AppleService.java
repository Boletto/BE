package com.demoboletto.service;

import com.demoboletto.client.AppleFeignClient;
import com.demoboletto.domain.User;
import com.demoboletto.dto.response.*;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.type.EProvider;

import com.demoboletto.type.ERole;
import com.demoboletto.utility.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;


import lombok.*;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class AppleService {
    private final AppleFeignClient appleAuthKeyFeignClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private static final String EMAIL_CLAIM = "email";

    public EProvider getProvider() {
        return EProvider.APPLE;
    }

    public OAuthUserInformation requestUserInformation(String token) {
        try {
            // Apple의 공개 키 가져오기 및 JWT 파싱
            Keys keys = getApplePublicKeys();
            SignedJWT signedJWT = SignedJWT.parse(token);

            // 서명이 유효하면 사용자 정보를 반환
            if (isVerifiedToken(keys, signedJWT)) {
                return extractUserInfo(signedJWT);
            } else {
                log.warn("[AppleOAuthServiceImpl] requestUserInformation, 유효하지 않은 토큰 : {}", token);
                throw new CommonException(ErrorCode.INVALID_APPLE_TOKEN);
            }

        } catch (JsonProcessingException | ParseException | JOSEException e) {
            log.error("[AppleOAuthServiceImpl] requestUserInformation, Exception: {}", token, e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // Apple에서 공개 키를 가져오고 JSON을 Keys 객체로 변환
    private Keys getApplePublicKeys() throws JsonProcessingException {
        String publicKeys = appleAuthKeyFeignClient.call();
        return objectMapper.readValue(publicKeys, Keys.class);
    }

    // JWT의 서명을 검증
    private boolean isVerifiedToken(Keys keys, SignedJWT signedJWT) throws
            ParseException,
            JOSEException,
            JsonProcessingException {
        return keys.getKeyList().stream()
                .anyMatch(key -> verifySignature(key, signedJWT));
    }

    // 개별 키에 대해 서명을 검증
    private boolean verifySignature(Keys.Key key, SignedJWT signedJWT) {
        try {
            RSAKey rsaKey = (RSAKey)JWK.parse(objectMapper.writeValueAsString(key));
            RSAPublicKey publicKey = rsaKey.toRSAPublicKey();
            RSASSAVerifier verifier = new RSASSAVerifier(publicKey);

            return signedJWT.verify(verifier);
        } catch (Exception e) {
            log.error("[AppleOAuthServiceImpl] 서명 검증 실패: {}", key.getKid(), e);
            return false;
        }
    }

    // JWT에서 사용자 정보를 추출
    private OAuthUserInformation extractUserInfo(SignedJWT signedJWT) throws ParseException {
        JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
        return AppleLoginInformation.builder()
                .providerId(jwtClaimsSet.getSubject())
                .email(jwtClaimsSet.getStringClaim(EMAIL_CLAIM))
                .build();
    }

    @Transactional
    public AppleLoginResponseDto login(String token) {
        OAuthUserInformation userInformation = requestUserInformation(token);
        User user;

        if (isExistsByProviderAndSerialId(EProvider.APPLE, userInformation.getProviderId())) {
            log.info("[UserService] login, response: {}", userInformation);
            user = findBySerialId(userInformation.getProviderId());
        } else {
            log.info("User logged in for the first time, response: {}", userInformation);
            user = saveUser(userInformation);
        }

        JwtTokenDto tokens = jwtUtil.generateTokens(user.getId(), ERole.USER);

        return new AppleLoginResponseDto(
                tokens.accessToken(),
                tokens.refreshToken(),
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getUserProfile()
        );
    }

    private User saveUser(OAuthUserInformation userInformation) {
        User user = User.builder()
                .email(userInformation.getEmail())
                .name(userInformation.getNickname())
                .provider(userInformation.getProvider())
                .serialId(userInformation.getProviderId())
                .userProfile(userInformation.getProfileImgUrl())
                .role(ERole.USER)
                .build();
        userRepository.save(user);

        return user;
    }

    private boolean isExistsByProviderAndSerialId(EProvider provider, String serialId) {
        return userRepository.existsByProviderAndProviderId(provider, serialId);
    }

    private User findBySerialId(String providerId) {
        return userRepository.findBySerialId(providerId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
    }

}
