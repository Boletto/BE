package com.demoboletto.dto.response;

import com.demoboletto.type.ETravelStatusType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Schema(name = "GetTravelDto", description = "request for getting travel list")
public record GetTravelDto(
        @NotNull(message = "travelId can not be null")
        @JsonProperty("travel_id") @Schema(description = "travel unique id", example = "1234")
        Long travelId,
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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate startDate,

        @NotNull(message = "endDate can not be null")
        @JsonProperty("end_date") @Schema(description = "travel end date", example = "2024-09-13")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate endDate,

        @NotNull(message = "members can not be null")
        @JsonProperty("members") @Schema(description = "travel members list", example = "[userTravelDto1, userTravelDto2]")
        List<GetUserTravelDto> members,

        @NotNull(message = "color can not be null")
        @JsonProperty("color") @Schema(description = "travel list color", example = "#FF0000")
        String color,

        @NotNull(message = "status can not be null")
        @Enumerated(EnumType.STRING)
        @JsonProperty("status") @Schema(description = "travel status", example = "UNLOCK")
        ETravelStatusType status,

        @JsonProperty("editable_user_id") @Schema(description = "editable user id", example = "1234")
        Long currentEditUserId,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonProperty("created_date") @Schema(description = "user travel created date", example = "2024-09-09 12:00:00")
        LocalDateTime createdDate
) {
}