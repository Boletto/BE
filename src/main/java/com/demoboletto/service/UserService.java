package com.demoboletto.service;

import com.demoboletto.exception.ErrorCode;
import com.demoboletto.domain.User;
import com.demoboletto.dto.request.UserProfileUpdateDto;
import com.demoboletto.dto.response.GetUserInfoDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저의 이름과 닉네임을 조회
    public GetUserInfoDto getUserNameAndNickname(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return GetUserInfoDto.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .build();
    }

    @Transactional
    public void updateUserProfile(UserProfileUpdateDto userProfileUpdateDto) {
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        user.updateProfile(
                userProfileUpdateDto.nickname(),
                userProfileUpdateDto.name(),
                userProfileUpdateDto.userProfile()
        );
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        userRepository.delete(user);
    }
}
