package com.demoboletto.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Schema(name = "CreateTravelDto", description = "request for creating new travel list")
public record CreateTravelDto(
        @NotNull(message = "departure can not be null")
        @JsonProperty("departure") @Schema(description = "location where to departure", example = "서울")
        String departure,
        @NotNull(message = "arrive can not be null")
        @JsonProperty("arrive") @Schema(description = "location where to arrive", example = "전주")
        String arrive,
        @NotNull(message = "keyword can not be null")
        @JsonProperty("keyword") @Schema(description = "travel types", example = "쇼핑, 관광")
        String keyword,
        @NotNull(message = "startDate can not be null")
        @JsonProperty("start_date") @Schema(description = "travel start date", example = "2024-09-09")
        LocalDate startDate,
        @NotNull(message = "endDate can not be null")
        @JsonProperty("end_date") @Schema(description = "travel end date", example = "2024-09-13")
        LocalDate endDate,
        @NotNull(message = "members can not be null")
        @JsonProperty("members") @Schema(description = "travel members list", example = "[12323,24234234]")
        List<Long> members,
        @NotNull(message = "color can not be null")
        @JsonProperty("color") @Schema(description = "travel list color", example = "#FF0000")
        String color
) {
}