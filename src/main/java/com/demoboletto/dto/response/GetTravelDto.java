package com.demoboletto.dto.response;

import com.demoboletto.domain.User;
import com.demoboletto.type.ETravelStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "GetTravelDto", description = "request for getting travel list")
public record GetTravelDto(
        @NotNull(message = "travelId can not be null")
        @JsonProperty("travelId") @Schema(description = "travel unique id", example = "1234")
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
        @JsonProperty("startDate") @Schema(description = "travel start date", example = "2024-09-09 10:30:00")
        String startDate,
        @NotNull(message = "endDate can not be null")
        @JsonProperty("endDate") @Schema(description = "travel end date", example = "2024-09-13 20:00:00")
        String endDate,
        @NotNull(message = "status can not be null")
        @JsonProperty("status") @Schema(description = "travel status", example = "PROCESS")
        @Enumerated(EnumType.STRING)
        ETravelStatus status,
        @NotNull(message = "owner can not be null")
        @JsonProperty("owner") @Schema(description = "travel owner", example = "12312323")
        Long owner,
        @NotNull(message = "friends can not be null")
        @JsonProperty("friends") @Schema(description = "travel friends list", example = "[user1,user2]")
        List<User> friends,
        @NotNull(message = "color can not be null")
        @JsonProperty("color") @Schema(description = "travel list color", example = "#FF0000")
        String color
) {
}