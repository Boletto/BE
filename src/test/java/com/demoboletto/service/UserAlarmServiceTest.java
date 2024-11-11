package com.demoboletto.service;

import com.demoboletto.dto.request.RecordUserAlarmDto;
import com.demoboletto.dto.response.UserAlarmDto;
import com.demoboletto.type.EAlarmType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("prod")
class UserAlarmServiceTest {

    private final Long testUserId = 41L;
    @Autowired
    private UserAlarmService userAlarmService;

    @Test
    void getUserAlarms() {
        List<UserAlarmDto> alarms = userAlarmService.getUserAlarms(testUserId);
        System.out.println("alarms = " + alarms);
    }

    @Test
    void updateUserAlarmIsRead() {

    }

    @Test
    void recordUserAlarm() {
        userAlarmService.recordUserAlarm(testUserId, RecordUserAlarmDto.builder()
                .alarmType(EAlarmType.STICKER_ACQUISITION)
                .value("되나요")
                .build());
    }
}