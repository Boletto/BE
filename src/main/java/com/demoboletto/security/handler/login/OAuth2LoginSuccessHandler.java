package com.demoboletto.security.handler.login;

import com.demoboletto.domain.User;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.type.EProvider;
import com.demoboletto.type.ERole;
import com.demoboletto.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("OAuth2 Login 성공!");

        // OAuth2 제공자의 사용자 정보를 가져옴
        Map<String, Object> attributes = ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttributes();
        String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();  // Provider 정보 (kakao, apple 등)
        EProvider provider = getProvider(registrationId);

        // 이메일과 소셜 제공자의 고유 ID(serialId) 추출
        String email = (String) attributes.get("email");
        String serialId = (String) attributes.get("sub"); // 애플의 경우 'sub'가 사용자 고유 ID

        User user = findOrCreateUser(serialId, email, provider);

        // JWT 토큰 발급
        String accessToken = jwtUtil.generateToken(user.getId(), user.getRole(), jwtUtil.getAccessExpiration());
        String refreshToken = jwtUtil.generateToken(user.getId(), user.getRole(), jwtUtil.getRefreshExpiration());

        // JWT 토큰 응답에 추가
        jwtUtil.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        log.info("OAuth2 로그인 성공: 이메일 = {}, AccessToken = {}", user.getEmail(), accessToken);
    }

    private User findOrCreateUser(String serialId, String email, EProvider provider) {
        Optional<User> existingUser = userRepository.findByProviderAndSerialId(provider, serialId);

        return existingUser.orElseGet(() -> {
            // 사용자 없으면 새로 생성
            User newUser = User.builder()
                    .serialId(serialId)
                    .email(email)
                    .provider(provider)
                    .role(ERole.USER)
                    .build();
            return userRepository.save(newUser);
        });
    }

    private EProvider getProvider(String registrationId) {
        switch (registrationId.toLowerCase()) {
            case "apple":
                return EProvider.APPLE;
            case "kakao":
                return EProvider.KAKAO;
            // 필요한 경우 다른 소셜 제공자 추가
            default:
                throw new IllegalArgumentException("알 수 없는 제공자: " + registrationId);
        }
    }

}

