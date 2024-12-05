package com.demoboletto.service;

import com.demoboletto.client.AppleFeignClient;
import com.demoboletto.domain.User;
import com.demoboletto.dto.oauth.AppleLoginDto;
import com.demoboletto.dto.oauth.AppleUserInformation;
import com.demoboletto.dto.oauth.JwtTokenDto;
import com.demoboletto.dto.oauth.Keys;
import com.demoboletto.dto.oauth.common.OAuthUserInformation;
import com.demoboletto.dto.response.AppleLoginResponseDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.utility.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;


@Slf4j
@RequiredArgsConstructor
@Service
public class AppleService {
    private static final String EMAIL_CLAIM = "email";
    private final AppleFeignClient appleAuthKeyFeignClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

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
            RSAKey rsaKey = (RSAKey) JWK.parse(objectMapper.writeValueAsString(key));
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
        return AppleUserInformation.builder()
                .providerId(jwtClaimsSet.getSubject())
                .email(jwtClaimsSet.getStringClaim(EMAIL_CLAIM))
                .build();
    }

    @Transactional
    public AppleLoginResponseDto login(AppleLoginDto appleLoginDto) {
        String token = appleLoginDto.identityToken();
        OAuthUserInformation userInformation = requestUserInformation(token);
        User user = userRepository.findBySerialId(userInformation.getSerialId())
                .orElseGet(() -> User.signUp(userInformation));

        JwtTokenDto tokens = jwtUtil.generateTokens(user.getId(), user.getRole());

        return new AppleLoginResponseDto(
                tokens.accessToken(),
                tokens.refreshToken(),
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getUserProfile()
        );
    }

}
