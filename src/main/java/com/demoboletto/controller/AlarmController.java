package com.demoboletto.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name="Alarm", description = "알람 관련 API")
@RequestMapping("/api/v1/alarm")
public class AlarmController {
}
