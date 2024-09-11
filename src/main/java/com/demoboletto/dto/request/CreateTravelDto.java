package com.demoboletto.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

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
        @JsonProperty("startDate") @Schema(description = "travel start date", example = "2024-09-09 10:30:00")
        String startDate,
        @NotNull(message = "endDate can not be null")
        @JsonProperty("endDate") @Schema(description = "travel end date", example = "2024-09-13 20:00:00")
        String endDate,
        @NotNull(message = "status can not be null")
        @JsonProperty("status") @Schema(description = "travel status", example = "process")
        String status,
        @NotNull(message = "owner can not be null")
        @JsonProperty("owner") @Schema(description = "travel owner", example = "홍길동")
        String owner
) {
}