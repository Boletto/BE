package com.demoboletto.controller;

import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.CreatePictureDto;
import com.demoboletto.dto.request.MemoryEditDto;
import com.demoboletto.service.MemoryService;
import com.demoboletto.service.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name="Memory", description = "추억 관련 API")
@RequestMapping("/api/v1/travel/memory")
public class MemoryController {
    private final MemoryService memoryService;
    @GetMapping("/get")
    @Operation(summary = "get memory list", description = "Get pictures, stickers, speech-bubble.")
    public ResponseDto<?> getMemoryList(@RequestParam(value = "travel_id") Long traveId) {
        return ResponseDto.ok(memoryService.getMemoryByTravelId(traveId));
    }
    @PatchMapping("/edit")
    @Operation(summary = "edit memory mode", description = "edit memory mode")
    public ResponseDto<?> memoryEditMode(@RequestBody MemoryEditDto memoryEditDtot) {
        return memoryService.memoryEditMode(memoryEditDtot) ? ResponseDto.ok("success") : ResponseDto.fail("fail");
    }
}
