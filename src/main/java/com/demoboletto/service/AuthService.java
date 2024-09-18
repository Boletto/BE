package com.demoboletto.service;

import com.demoboletto.dto.request.AuthSignUpDto;
import com.demoboletto.dto.response.JwtTokenDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.utility.JwtUtil;
import com.demoboletto.dto.request.OauthLoginDto;
import com.demoboletto.domain.User;
import com.demoboletto.type.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public boolean checkDuplicate(String email) {
        return userRepository.existsBySerialId(email);
    }

//    public void signUp(AuthSignUpDto authSignUpDto) {
//        userRepository.save(
//                User.signUp(authSignUpDto, bCryptPasswordEncoder.encode(authSignUpDto.password()))
//        );
//    }

    @Transactional
    public JwtTokenDto login(OauthLoginDto userLoginDto) {
        User user;
        boolean isNewUser = false;

        Optional<User> existingUser = userRepository.findBySerialId(userLoginDto.serialId());

        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            // 새로운 사용자인 경우 회원 정보 저장
            user = userRepository.save(User.signUp(userLoginDto.serialId(), userLoginDto.provider(), userLoginDto.nickname()));
            isNewUser = true;
        }

        //JWT 토큰 생성
        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(user.getId(), ERole.USER);

        // 신규 사용자이거나 리프레시 토큰이 변경된 경우 갱신
        if (isNewUser || !jwtTokenDto.refreshToken().equals(user.getRefreshToken())) {
            userRepository.updateRefreshTokenAndLoginStatus(user.getId(), jwtTokenDto.refreshToken(), true);
        }

        return jwtTokenDto;
    }

    @Transactional
    public JwtTokenDto reissue(Long userId, String refreshToken) {
        User user = userRepository.findByIdAndRefreshTokenAndIsLogin(userId, refreshToken, true)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LOGIN_USER));

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(user.getId(), user.getRole());
        user.updateRefreshToken(jwtTokenDto.refreshToken());

        return jwtTokenDto;
    }
}
