package com.demoboletto.service;

import com.demoboletto.domain.SysFrame;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserFrame;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.SysFrameRepository;
import com.demoboletto.repository.UserFrameRepository;
import com.demoboletto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFrameService {
    private final UserFrameRepository userFrameRepository;
    private final SysFrameRepository sysFrameRepository;
    private final UserRepository userRepository;

    public void saveUserFrames(Long userId, String frameCode) {
        SysFrame sysFrame = sysFrameRepository.findByFrameCode(frameCode)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SYS_FRAME));
        User user = getUser(userId);
        UserFrame userFrame = UserFrame.builder()
                .user(user)
                .frame(sysFrame)
                .build();
        
        try {
            userFrameRepository.save(userFrame);
        } catch (DataIntegrityViolationException e) {
            throw new CommonException(ErrorCode.ALREADY_COLLECTED_FRAME);
        }

    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
    }
}
