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
@RequestMapping("/api/v1/travel")
public class TravelController {
    private final TravelService travelService;

    @PostMapping("/create")
    @Operation(summary = "create new travel list", description = "Create new travel list if there are enough data to create travel data.")
    public ResponseDto<?> createTravelList(@RequestBody CreateTravelDto travelDto, @Parameter(hidden = true) @UserId Long userId) {
        return travelService.createTravelList(travelDto, userId) ? ResponseDto.ok("success") : ResponseDto.fail("existing data");
    }

    @GetMapping(value = "/get/all")
    @Operation(summary = "get all travel list", description = "Get all travel list.")
    public ResponseDto<List<GetTravelDto>> getAllTravelList(@Parameter(hidden = true) @UserId Long userId) {
        return ResponseDto.ok(travelService.getAllTravelList(userId));
    }

    @GetMapping(value = "/get", params = "travel_id")
    @Operation(summary = "get one travel list", description = "Get one travel list.")
    public ResponseDto<GetTravelDto> getTravelList(@RequestParam(value = "travel_id") Long id) {
        return ResponseDto.ok(travelService.getTravelList(id));
    }

    @PatchMapping("/update")
    @Operation(summary = "update travel list", description = "Update travel list.")
    public ResponseDto<GetTravelDto> updateTravelList(@RequestBody UpdateTravelDto travelDto) {
        return ResponseDto.ok(travelService.updateTravelList(travelDto));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete travel list", description = "Delete travel list.")
    public ResponseDto<?> deleteTravelList(@RequestParam(value = "travel_id") Long id) {
        return travelService.deleteTravelList(id) ? ResponseDto.ok("success") : ResponseDto.fail("fail");
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
