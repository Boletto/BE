package com.demoboletto.service;

import com.demoboletto.domain.User;
import com.demoboletto.dto.oauth.AppleLoginDto;
import com.demoboletto.dto.oauth.AppleUserInformation;
import com.demoboletto.dto.oauth.JwtTokenDto;
import com.demoboletto.dto.oauth.apple.AppleTokenResponse;
import com.demoboletto.dto.oauth.common.OAuthUserInformation;
import com.demoboletto.dto.response.AppleLoginResponseDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.utility.AppleTokenUtil;
import com.demoboletto.utility.JwtUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;


@Slf4j
@RequiredArgsConstructor
@Service
public class AppleService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final RestTemplate restTemplate;

    @Value("${apple.client-id}")
    private String clientId;

    @Value("${apple.team-id}")
    private String teamId;

    @Value("${apple.key-id}")
    private String keyId;

    @Value("${apple.key-path}")
    private String keyPath;

    @Value("${apple.token-url}")
    private String tokenUrl;

    @Value("${apple.revoke-url}")
    private String revokeUrl;

    @Transactional
    public AppleLoginResponseDto login(AppleLoginDto appleLoginDto) {
        String code = appleLoginDto.code();
        String name = appleLoginDto.userName();

        try {
            AppleTokenResponse tokenResponse = getToken(code);

            OAuthUserInformation userInfo = extractUserInfo(tokenResponse.getIdToken());

            User user = userRepository.findBySerialId(userInfo.getSerialId())
                    .orElseGet(() -> authService.signUp(userInfo));

            JwtTokenDto tokens = jwtUtil.generateTokens(user.getId(), user.getRole());
            if (name != null) {
                user.updateName(name);
            }
            user.updateRefreshToken(tokens.refreshToken());
            user.updateAppleRefreshToken(tokenResponse.getRefreshToken());
            userRepository.save(user);

            return new AppleLoginResponseDto(
                    tokens.accessToken(),
                    tokens.refreshToken(),
                    user.getId(),
                    user.getName(),
                    user.getNickname(),
                    user.getUserProfile()
            );
        } catch (ParseException e) {
            log.error("[AppleService] login, Exception: {}", e.getMessage());
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (JOSEException e) {
            log.error("[AppleService] login, Exception: {}", e.getMessage());
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("[AppleService] login, Exception: {}", e.getMessage());
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    private AppleTokenResponse getToken(String authorizationCode) throws Exception {
        String clientSecret = AppleTokenUtil.createClientSecret(clientId, teamId, keyId, keyPath);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", authorizationCode);
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(tokenUrl, request, AppleTokenResponse.class);
    }

    private OAuthUserInformation extractUserInfo(String idToken) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(idToken);
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

        return new AppleUserInformation(
                claims.getSubject(), // Unique Apple ID
                claims.getStringClaim("email")
        );
    }

    public void revokeAppleToken(String appleRefreshToken) {
        try {
            String appleClientSecret = AppleTokenUtil.createClientSecret(clientId, teamId, keyId, keyPath);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("client_id", clientId);
            map.add("client_secret", appleClientSecret);
            map.add("token", appleRefreshToken);
            map.add("token_type_hint", "refresh_token");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            restTemplate.postForLocation(revokeUrl, request);
        } catch (Exception e) {
            log.error("[AppleService] revokeAppleToken, Exception: {}", e.getMessage());
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
