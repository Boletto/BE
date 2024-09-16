package com.demoboletto.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "UpdateTravelDto", description = "request for updating new travel list")
public record UpdateTravelDto(
        @NotNull(message = "travelId can not be null")
        @JsonProperty("travel_id") @Schema(description = "travel unique id", example = "1234")
        Long travelId,
        @JsonProperty("departure") @Schema(description = "location where to departure", example = "서울")
        String departure,
        @JsonProperty("arrive") @Schema(description = "location where to arrive", example = "전주")
        String arrive,
        @JsonProperty("keyword") @Schema(description = "travel types", example = "쇼핑, 관광")
        String keyword,
        @JsonProperty("startDate") @Schema(description = "travel start date", example = "2024-09-09 10:30:00")
        String startDate,
        @JsonProperty("endDate") @Schema(description = "travel end date", example = "2024-09-13 20:00:00")
        String endDate,
        @JsonProperty("members") @Schema(description = "travel members list", example = "[12323,24234234]")
        List<Long> members,
        @JsonProperty("color") @Schema(description = "travel list color", example = "#FF0000")
        String color
) {
}