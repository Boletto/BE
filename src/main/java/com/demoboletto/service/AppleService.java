package com.demoboletto.service;

import com.demoboletto.domain.User;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.type.EProvider;
import com.demoboletto.type.ERole;
import com.demoboletto.utility.JwtUtil;

import org.json.JSONObject;
import com.nimbusds.jwt.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.PrivateKey;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppleService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Value("${apple.team-id}")
    private String APPLE_TEAM_ID;

    @Value("${apple.login-key}")
    private String APPLE_LOGIN_KEY;

    @Value("${apple.client-id}")
    private String APPLE_CLIENT_ID;

    @Value("${apple.redirect-url}")
    private String APPLE_REDIRECT_URL;

    @Value("${apple.key-path}")
    private String APPLE_KEY_PATH;

    private final static String APPLE_AUTH_URL = "https://appleid.apple.com";

    public String getAppleLoginUrl() {
        return APPLE_AUTH_URL + "/auth/authorize"
                + "?client_id=" + APPLE_CLIENT_ID
                + "&redirect_uri=" + APPLE_REDIRECT_URL
                + "&response_type=code%20id_token&scope=name%20email&response_mode=form_post";
    }

    public User loginwithApple(String code, HttpServletResponse response) {

        String userId;
        String email;
        String accessToken;

        User user;

        try {
            // Apple로부터 토큰 정보를 받아옴
            JSONObject jsonObj = new JSONObject(generateAuthToken(code));
            accessToken = jsonObj.getString("access_token");

            // ID TOKEN을 통해 회원 고유 식별자 받기
            SignedJWT signedJWT = SignedJWT.parse(String.valueOf(jsonObj.get("id_token")));
            JWTClaimsSet getPayload = signedJWT.getJWTClaimsSet();

            userId = getPayload.getSubject();  // 'sub' 값을 통해 애플 사용자 ID 가져오기
            email = getPayload.getStringClaim("email");  // 이메일 가져오기

            Optional<User> existingUser = userRepository.findByProviderAndSerialId(EProvider.APPLE, userId);

            if (existingUser.isPresent()) {
                user = existingUser.get();
                String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getRole());

                // RefreshToken 업데이트
                userRepository.updateRefreshTokenAndLoginStatus(user.getId(), refreshToken, true);
            } else {
                // 신규 회원 가입
                user = userRepository.save(User.builder()
                        .serialId(userId)
                        .email(email)
                        .provider(EProvider.APPLE)
                        .role(ERole.GUEST)
                        .build());
            }

            // Access 토큰 & RefreshToken 발급
            loginSuccess(user, response);
            return user;

        } catch(Exception e){
            throw new RuntimeException("Apple login failed", e);
        }
    }

    public void loginSuccess(User user, HttpServletResponse response) {

        String accessToken = jwtUtil.generateToken(user.getId(), user.getRole(), jwtUtil.getAccessExpiration());
        String refreshToken = jwtUtil.generateToken(user.getId(), user.getRole(), jwtUtil.getRefreshExpiration());

        // AccessToken과 RefreshToken을 응답 헤더에 추가
        jwtUtil.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        userRepository.updateRefreshTokenAndLoginStatus(user.getId(), refreshToken, true);
    }


    public String generateAuthToken(String code) throws IOException {
        if (code == null) throw new IllegalArgumentException("Failed get authorization code");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", APPLE_CLIENT_ID);
        params.add("client_secret", createClientSecretKey());
        params.add("code", code);
        params.add("redirect_uri", APPLE_REDIRECT_URL);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    APPLE_AUTH_URL + "/auth/token",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("Apple Auth Token Error");
        }
    }

    public String createClientSecretKey() throws IOException {
        // headerParams 적재
        Map<String, Object> headerParamsMap = new HashMap<>();
        headerParamsMap.put("kid", APPLE_LOGIN_KEY);
        headerParamsMap.put("alg", "ES256");

        // clientSecretKey 생성
        return Jwts
                .builder()
                .setHeaderParams(headerParamsMap)
                .setIssuer(APPLE_TEAM_ID)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발행시간
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 30)) // 만료 시간 (30초)
                .setAudience(APPLE_AUTH_URL)
                .setSubject(APPLE_CLIENT_ID)
                .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    private PrivateKey getPrivateKey() throws IOException {
        ClassPathResource resource = new ClassPathResource(APPLE_KEY_PATH);
        String privateKey = new String(resource.getInputStream().readAllBytes());

        Reader pemReader = new StringReader(privateKey);
        PEMParser pemParser = new PEMParser(pemReader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();

        return converter.getPrivateKey(object);
    }


}
