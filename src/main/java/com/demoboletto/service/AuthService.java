package com.demoboletto.service;

import com.demoboletto.domain.User;
import com.demoboletto.dto.oauth.JwtTokenDto;
import com.demoboletto.dto.oauth.common.OAuthUserInformation;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.utility.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public User signUp(OAuthUserInformation userInfo) {
        User user = User.signUp(userInfo);
        entityManager.persist(user);
        return user;
    }

    @Transactional
    public JwtTokenDto reissue(String refreshToken) {
        try {
            jwtUtil.validateToken(refreshToken);
        } catch (ExpiredJwtException e) {
            log.error("[AuthService] reissue, Expired refresh token: {}", refreshToken);
            throw new CommonException(ErrorCode.EXPIRED_TOKEN_ERROR);
        }
        User user = userRepository.findUserByRefreshToken(refreshToken)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(user.getId(), user.getRole());
        user.updateRefreshToken(jwtTokenDto.refreshToken());

        return jwtTokenDto;
    }
}
