package com.demoboletto.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
@Schema(name = "UpdateTravelDto", description = "request for updating new travel list")
public record UpdateTravelDto(
        @JsonProperty("departure") @Schema(description = "location where to departure", example = "서울")
        String departure,
        @JsonProperty("arrive") @Schema(description = "location where to arrive", example = "전주")
        String arrive,
        @JsonProperty("keyword") @Schema(description = "travel types", example = "쇼핑, 관광")
        String keyword,
        @JsonProperty("start_date") @Schema(description = "travel start date", example = "2024-09-09")
        LocalDate startDate,
        @JsonProperty("end_date") @Schema(description = "travel end date", example = "2024-09-13")
        LocalDate endDate,
        @JsonProperty("members") @Schema(description = "travel members list", example = "[12323,24234234]")
        List<Long> members,
        @JsonProperty("color") @Schema(description = "travel list color", example = "#FF0000")
        String color
) {
}