package com.demoboletto.service;

import com.demoboletto.domain.User;
import com.demoboletto.domain.UserAlarm;
import com.demoboletto.dto.request.RecordUserAlarmDto;
import com.demoboletto.dto.response.UserAlarmDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.UserAlarmRepository;
import com.demoboletto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAlarmService {
    private final UserAlarmRepository userAlarmRepository;
    private final UserRepository userRepository;

    public List<UserAlarmDto> getUserAlarms(Long userId) {
        List<UserAlarm> alarms = userAlarmRepository.findByUserId(userId);

        return alarms.stream()
                .map(UserAlarmDto::of)
                .toList();
    }

    public void updateUserAlarmIsRead(Long userId, Long alarmId) {
        UserAlarm alarm = userAlarmRepository.findById(alarmId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        if (!alarm.getUser().getId().equals(userId)) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        alarm.markAsRead();
        userAlarmRepository.save(alarm);
    }

    public void recordUserAlarm(Long userId, RecordUserAlarmDto userAlarmDto) {
        User user = getUser(userId);
        UserAlarm alarm = UserAlarm.builder()
                .user(user)
                .alarmType(userAlarmDto.alarmType())
                .value(userAlarmDto.value())
                .build();
        userAlarmRepository.save(alarm);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }
}

