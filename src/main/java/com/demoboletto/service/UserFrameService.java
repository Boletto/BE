package com.demoboletto.service;

import com.demoboletto.domain.SysFrame;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserFrame;
import com.demoboletto.dto.response.GetUserUsableFrameDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.SysFrameRepository;
import com.demoboletto.repository.UserFrameRepository;
import com.demoboletto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<GetUserUsableFrameDto> getUsableFrames(Long userId) {
        // Retrieve default frames
        List<SysFrame> defaultFrames = sysFrameRepository.findDefaultFrames();

        // Retrieve user-specific frames
        List<UserFrame> userFrames = userFrameRepository.findAllByUserId(userId);

        // Map default frames to DTOs with `isOwned=false`
        Map<Long, GetUserUsableFrameDto> frameMap = defaultFrames.stream()
                .collect(Collectors.toMap(
                        SysFrame::getFrameId,
                        GetUserUsableFrameDto::of
                ));

        // Override with user-owned frames (`isOwned=true`)
        for (UserFrame userFrame : userFrames) {
            frameMap.put(userFrame.getFrame().getFrameId(), GetUserUsableFrameDto.of(userFrame));
        }

        // Return as a list
        return new ArrayList<>(frameMap.values());
    }
}
