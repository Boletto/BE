package com.demoboletto.service;

import com.demoboletto.domain.Analysis;
import com.demoboletto.domain.User;
import com.demoboletto.dto.RecordUserActionDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.AnalysisRepository;
import com.demoboletto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalysisService {
    private final AnalysisRepository analysisRepository;
    private final UserRepository userRepository;

    public void recordEvent(Long userId, RecordUserActionDto recordUserActionDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        Analysis analysis = Analysis.builder()
                .user(user)
                .actionType(recordUserActionDto.actionType())
                .actionDetails(recordUserActionDto.actionDetails())
                .build();
        analysisRepository.save(analysis);
        log.debug("Event recorded successfully");
    }
}
