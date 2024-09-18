package com.demoboletto.security.service;


import com.demoboletto.domain.User;
import com.demoboletto.repository.UserRepository;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.demoboletto.type.EProvider;
import com.demoboletto.type.ERole;
import com.demoboletto.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 소셜 로그인 공급자 정보 (애플, 카카오 등)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // 소셜 로그인으로부터 가져온 사용자 정보
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String oauth2AccessToken = userRequest.getAccessToken().getTokenValue();

        // 사용자 정보 매핑 및 저장/조회
        User user = processUser(registrationId, userNameAttributeName, attributes, oauth2AccessToken);
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())),
                attributes,
                userNameAttributeName
        );
    }

    private User processUser(String registrationId, String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        // 소셜 로그인 공급자에 맞게 사용자 정보 추출 (카카오, 애플 등)
        String serialId = (String) attributes.get(userNameAttributeName);  // 예: sub, id 등
        String email = (String) attributes.get("email");

        // 기존 사용자 조회 또는 새로 생성
        Optional<User> existingUser = userRepository.findByProviderAndSerialId(EProvider.valueOf(registrationId.toUpperCase()), serialId);

        if (existingUser.isPresent()) {
            // 기존 사용자면 액세스 토큰을 업데이트
            User user = existingUser.get();
            return userRepository.save(user);
        }

        // 신규 사용자 생성
        User newUser = User.builder()
                .serialId(serialId)
                .email(email)
                .provider(EProvider.valueOf(registrationId.toUpperCase()))  // 공급자 정보 저장
                .role(ERole.USER)  // 기본 역할 설정
                .build();
        return userRepository.save(newUser);
    }
}

