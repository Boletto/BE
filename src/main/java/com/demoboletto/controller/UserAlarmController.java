package com.demoboletto.controller;

import com.demoboletto.annotation.UserId;
import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.RecordUserAlarmDto;
import com.demoboletto.dto.response.UserAlarmDto;
import com.demoboletto.service.UserAlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "UserAlarm", description = "유저 알람 관련 API")
@RequestMapping("/api/v1/user/alarm")
public class UserAlarmController {

    private final UserAlarmService userAlarmService;

    @GetMapping
    @Operation(summary = "Get user alarms", description = "유저의 알람을 조회합니다.")
    public ResponseDto<List<UserAlarmDto>> getUserAlarms(@Parameter(hidden = true) @UserId Long userId) {
        return ResponseDto.ok(userAlarmService.getUserAlarms(userId));
    }

    @PutMapping("/{alarmId}")
    @Operation(summary = "Update user alarm", description = "유저의 알람을 수정합니다.")
    public ResponseDto<?> updateUserAlarmIsRead(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long alarmId) {
        userAlarmService.updateUserAlarmIsRead(userId, alarmId);
        return ResponseDto.ok("success");
    }

    @PostMapping
    @Operation(summary = "Record client side user alarm", description = "클라이언트 측에서 발생한 알람을 기록합니다.")
    public ResponseDto<?> recordUserAlarm(@Parameter(hidden = true) @UserId Long userId, @RequestBody RecordUserAlarmDto recordUserAlarmDto) {
        userAlarmService.recordUserAlarm(userId, recordUserAlarmDto);
        return ResponseDto.ok("success");
    }


}
