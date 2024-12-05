package com.demoboletto.controller;

import com.demoboletto.annotation.UserId;
import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.CreateTravelDto;
import com.demoboletto.dto.request.UpdateTravelDto;
import com.demoboletto.dto.request.UpdateTravelStatusDto;
import com.demoboletto.dto.response.GetTravelDto;
import com.demoboletto.service.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Travel", description = "Travel API")
@RequestMapping("/api/v1/travels")
public class TravelController {
    private final TravelService travelService;

    @PostMapping("")
    @Operation(summary = "create new travel list", description = "Create new travel list if there are enough data to create travel data.")
    public ResponseDto<?> createTravel(@RequestBody CreateTravelDto travelDto, @Parameter(hidden = true) @UserId Long userId) {
        travelService.createTravel(travelDto, userId);
        return ResponseDto.ok("success");
    }

    @GetMapping(value = "")
    @Operation(summary = "get all travel list", description = "Get all travel list.")
    public ResponseDto<List<GetTravelDto>> getAllTravelList(
            @Parameter(hidden = true) @UserId Long userId,
            @RequestParam boolean isAccepted
    ) {
        return ResponseDto.ok(travelService.getAllTravels(userId, isAccepted));
    }

    @GetMapping("/{travelId}")
    @Operation(summary = "get travel", description = "Get travel")
    public ResponseDto<GetTravelDto> getTravel(@PathVariable Long travelId) {
        return ResponseDto.ok(travelService.getTravel(travelId));
    }


    @PatchMapping("/{travelId}")
    @Operation(summary = "update travel", description = "Update travel list.")
    public ResponseDto<GetTravelDto> updateTravelList(@PathVariable Long travelId, @RequestBody UpdateTravelDto travelDto) {
        return ResponseDto.ok(travelService.updateTravelList(travelId, travelDto));
    }

    @PatchMapping("/{travelId}/accept")
    @Operation(summary = "여행 수락", description = "여행 수락")
    public ResponseDto<?> acceptTravel(@PathVariable Long travelId, @Parameter(hidden = true) @UserId Long userId) {
        travelService.acceptTravel(travelId, userId);
        return ResponseDto.ok("Travel invitation accepted.");
    }

    @PatchMapping("/{travelId}/reject")
    @Operation(summary = "여행 거부", description = "여행 거부")
    public ResponseDto<?> rejectTravel(@PathVariable Long travelId, @Parameter(hidden = true) @UserId Long userId) {
        travelService.rejectTravel(travelId, userId);
        return ResponseDto.ok("Travel invitation rejected.");
    }


    @DeleteMapping("/{travelId}")
    @Operation(summary = "delete travel", description = "Delete travel list.")
    public ResponseDto<?> deleteTravel(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long travelId) {
        travelService.deleteTravel(userId, travelId);
        return ResponseDto.ok("Travel deleted.");
    }

    @PutMapping("{travelId}/status")
    @Operation(summary = "update travel editable", description = "편집 LOCK/UNLOCK")
    public ResponseDto<String> updateTravelEditable(
            @Parameter(hidden = true) @UserId Long userId,
            @PathVariable Long travelId,
            @RequestBody UpdateTravelStatusDto updateTravelStatusDto) {
        travelService.updateTravelEditable(userId, travelId, updateTravelStatusDto);
        return ResponseDto.ok("success");
    }
}
