package com.demoboletto.controller;

import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.CreateTravelDto;
import com.demoboletto.service.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name="Travel", description = "Travel API")
@RequestMapping("/api/v1/travel")
public class TravelController {
    private final TravelService travelService;

    @PostMapping("/")
    @Operation(summary = "create new travel list", description = "Create new travel list if there are enough data to create travel data.")
    public ResponseDto<?> createTravelList(@RequestBody CreateTravelDto travelDto) {
        return travelService.createTravelList(travelDto) ? ResponseDto.ok("success") : ResponseDto.fail("existing data");
    }
    @GetMapping("/")
    @Operation(summary = "get all travel list", description = "Get all travel list.")
    public ResponseDto<?> getAllTravelList() {
        return ResponseDto.ok(travelService.getAllTravelList());
    }
    @GetMapping(value = "/", params = "id")
    @Operation(summary = "get one travel list", description = "Get one travel list.")
    public ResponseDto<?> getTravelList(@RequestParam(value = "id") Long id) {
        return ResponseDto.ok(travelService.getTravelList(id));
    }
}