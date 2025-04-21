package com.demoboletto.controller;

import com.demoboletto.annotation.UserId;
import com.demoboletto.dto.RecordUserActionDto;
import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.service.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Analysis", description = "유저 행동 분석을 위한 record 관련 API")
@RequestMapping("/api/v1/analysis")
public class AnalysisController {
    private final AnalysisService analysisService;


    @PostMapping("/event")
    @Operation(summary = "record event", description = "유저 행동 분석을 위한 record API")
    public ResponseDto<?> recordEvent(
            @Parameter(hidden = true) @UserId Long userId,
            @RequestBody RecordUserActionDto recordUserActionDto
    ) {
        analysisService.recordEvent(userId, recordUserActionDto);
        return ResponseDto.ok("Event recorded successfully");
    }
}
