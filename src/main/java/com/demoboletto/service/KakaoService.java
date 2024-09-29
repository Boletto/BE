package com.demoboletto.service;

import com.demoboletto.dto.request.OauthLoginDto;
import com.demoboletto.dto.response.JwtTokenDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.domain.User;
import com.demoboletto.type.EProvider;
import com.demoboletto.type.ERole;
import com.demoboletto.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.demoboletto.dto.response.OAuthLoginResponseDto;

import java.util.Optional;

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
    public JwtTokenDto reissue(Long userId, String refreshToken) {
        User user = userRepository.findByIdAndRefreshTokenAndIsLogin(userId, refreshToken, true)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LOGIN_USER));

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(user.getId(), user.getRole());
        user.updateRefreshToken(jwtTokenDto.refreshToken());

        return jwtTokenDto;
    }

    @Transactional
    public OAuthLoginResponseDto login(OauthLoginDto userLoginDto) {
        User user;
        boolean isNewUser = false;

        Optional<User> existingUser = userRepository.findBySerialId(userLoginDto.serialId());

        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            user = userRepository.save(User.signUp(userLoginDto.serialId(), userLoginDto.provider(), userLoginDto.nickname()));
            isNewUser = true;
            log.info("User with serialId: {} logged in for the first time.", userLoginDto.serialId());
        }

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(user.getId(), ERole.USER);

        if (isNewUser || !jwtTokenDto.refreshToken().equals(user.getRefreshToken())) {
            userRepository.updateRefreshTokenAndLoginStatus(user.getId(), jwtTokenDto.refreshToken(), true);
        }

        return new OAuthLoginResponseDto(
                jwtTokenDto.accessToken(),
                jwtTokenDto.refreshToken(),
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getUserProfile()
        );
    }

    public boolean checkDuplicate(String email) {
        return userRepository.existsBySerialId(email);
    }


}
