package com.demoboletto.service;

import com.demoboletto.domain.User;
import com.demoboletto.dto.oauth.JwtTokenDto;
import com.demoboletto.dto.oauth.KakaoUserInformation;
import com.demoboletto.dto.oauth.OAuthLoginResponseDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.type.EProvider;
import com.demoboletto.type.ERole;
import com.demoboletto.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public EProvider getProvider() {
        return EProvider.KAKAO;
    }

    @Transactional
    public JwtTokenDto reissue(String refreshToken) {
        User user = userRepository.findUserByRefreshToken(refreshToken)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(user.getId(), user.getRole());
        user.updateRefreshToken(jwtTokenDto.refreshToken());

        return jwtTokenDto;
    }

    @Transactional
    public OAuthLoginResponseDto login(KakaoUserInformation kakaoUserInformation) {
        User user = userRepository.findBySerialId(kakaoUserInformation.serialId())
                .orElseGet(() -> User.signUp(kakaoUserInformation));

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(user.getId(), ERole.USER);
        user.updateRefreshToken(jwtTokenDto.refreshToken());
        userRepository.save(user);

        return new OAuthLoginResponseDto(
                jwtTokenDto.accessToken(),
                jwtTokenDto.refreshToken(),
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getUserProfile()
        );
    }
}
