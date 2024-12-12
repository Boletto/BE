package com.demoboletto.controller;

import com.demoboletto.service.AlarmService;
import com.demoboletto.type.ESilentEventType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/test")
@RequiredArgsConstructor
public class TestController {
    private final AlarmService alarmService;

    @Operation(summary = "모든 이벤트 발송", description = "프레임, 스티커 관련 모든 silent 이벤트를 발송합니다.")
    @GetMapping("/dispatchAll")
    public void dispatchAll() {
        alarmService.dispatchSystemEvent(ESilentEventType.SYSTEM_STICKER_UPDATE);
        alarmService.dispatchSystemEvent(ESilentEventType.SYSTEM_FRAME_UPDATE);
        alarmService.dispatchSystemEvent(ESilentEventType.SYSTEM_EVENT_STICKER_UPDATE);
        alarmService.dispatchSystemEvent(ESilentEventType.SYSTEM_EVENT_FRAME_UPDATE);
    }
}
