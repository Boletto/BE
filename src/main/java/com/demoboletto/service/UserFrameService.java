package com.demoboletto.service;

import com.demoboletto.domain.SysFrame;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserCustomFrame;
import com.demoboletto.domain.UserFrame;
import com.demoboletto.dto.response.GetUserUsableFrameDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.SysFrameRepository;
import com.demoboletto.repository.UserCustomFrameRepository;
import com.demoboletto.repository.UserFrameRepository;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.type.EFrameType;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final ObjectStorageService objectStorageService;
    private final UserCustomFrameRepository userCustomFrameRepository;
    private final EntityManager entityManager;

    public void saveUserFrame(Long userId, String frameCode) {
        SysFrame sysFrame = sysFrameRepository.findByFrameCode(frameCode)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SYS_FRAME));
        if (sysFrame.isEventExpired()) {
            throw new CommonException(ErrorCode.EVENT_EXPIRED);

        }
        User user = getUser(userId);
        UserFrame userFrame = UserFrame.builder()
                .frameType(EFrameType.SYSTEM)
                .user(user)
                .sysFrame(sysFrame)
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
        Map<String, GetUserUsableFrameDto> frameMap = defaultFrames.stream()
                .collect(Collectors.toMap(
                        SysFrame::getFrameCode,
                        GetUserUsableFrameDto::of
                ));

        // Override with user-owned frames (`isOwned=true`)
        for (UserFrame userFrame : userFrames) {
            frameMap.put(userFrame.getFrameCode(), GetUserUsableFrameDto.of(userFrame));
        }

        // Return as a list
        return new ArrayList<>(frameMap.values());
    }

    @Transactional
    public GetUserUsableFrameDto createUserCustomFrame(Long userId, MultipartFile file) {
        // 1. 파일 업로드
        String frameUrl;
        try {
            frameUrl = objectStorageService.uploadUserFrameFile(file, userId);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }

        // 2. UserCustomFrame 생성 및 ID 미리 가져오기
        UserCustomFrame userCustomFrame = UserCustomFrame.builder()
                .frameUrl(frameUrl)
                .build();

        entityManager.persist(userCustomFrame); // 여기서 frameId가 생성됨
        userCustomFrame.setFrameCode();        // frameId를 사용해 frameCode 설정

        // 3. 다시 저장하지 않아도 트랜잭션 종료 시 UPDATE 쿼리 자동 실행
        UserFrame userFrame = UserFrame.builder()
                .user(getUser(userId))
                .customFrame(userCustomFrame)
                .frameType(EFrameType.CUSTOM)
                .build();
        userFrameRepository.save(userFrame);

        return GetUserUsableFrameDto.of(userFrame);
    }
}
