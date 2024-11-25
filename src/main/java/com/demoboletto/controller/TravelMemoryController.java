package com.demoboletto.controller;

import com.demoboletto.annotation.UserId;
import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.UpdateTravelEachMemoryDto;
import com.demoboletto.dto.request.UpdateTravelMemoryStickerDto;
import com.demoboletto.dto.response.GetTravelMemoryDto;
import com.demoboletto.service.TravelMemoryService;
import com.demoboletto.service.TravelStickerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Travel Memory", description = "Memory of travel")
@RequestMapping("/api/v1/travel/{travelId}/memory")
public class TravelMemoryController {
    private final TravelMemoryService travelMemoryService;
    private final TravelStickerService travelStickerService;

    @GetMapping
    @Operation(summary = "Get Travel Memory", description = "Get Travel Memory")
    public ResponseDto<GetTravelMemoryDto> getTravelMemory(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long travelId) {
        System.out.println("travel_id = " + travelId);
        return ResponseDto.ok(travelMemoryService.getTravelMemory(userId, travelId));
    }

    @PostMapping(value = "/{memoryIdx}", consumes = "multipart/form-data")
    @Operation(summary = "Create Travel Memory", description = "Create Travel Memory")
    public ResponseDto<String> updateTravelEachMemory(
            @Parameter(hidden = true) @UserId Long userId,
            @PathVariable Long travelId,
            @PathVariable Long memoryIdx,
            @RequestPart UpdateTravelEachMemoryDto updateTravelEachMemoryDto,
            @RequestPart List<MultipartFile> pictures
    ) {

        travelMemoryService.createTravelEachMemory(userId, travelId, memoryIdx, updateTravelEachMemoryDto, pictures);

        return ResponseDto.ok("Update Travel Memory " + memoryIdx);
    }

    @DeleteMapping("/{memoryIdx}")
    @Operation(summary = "Delete Travel Memory", description = "Delete Travel Memory")
    public ResponseDto<String> deleteTravelEachMemory(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long travelId, @PathVariable Long memoryIdx) {
        travelMemoryService.deleteTravelEachMemory(userId, travelId, memoryIdx);
        return ResponseDto.ok("Delete Travel Memory " + memoryIdx);
    }

    @PutMapping("/stickers")
    public ResponseDto<String> updateTravelMemoryStickers(
            @Parameter(hidden = true) @UserId Long userId,
            @PathVariable Long travelId,
            @RequestBody List<UpdateTravelMemoryStickerDto> stickers) {
        travelStickerService.updateTravelMemoryStickers(userId, travelId, stickers);
        return ResponseDto.ok("Update Travel Memory Stickers");
    }


//    @DeleteMapping
//    public String deleteTravelMemory(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long travelId) {
//        System.out.println("travel_id = " + travelId);
//        return "Delete Travel Memory";
//    }


}


