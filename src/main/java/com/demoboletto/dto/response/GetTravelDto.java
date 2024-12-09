package com.demoboletto.dto.response;

import com.demoboletto.type.ETravelStatusType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Schema(name = "GetTravelDto", description = "request for getting travel list")
public record GetTravelDto(
        @JsonProperty("travel_id") @Schema(description = "travel unique id", example = "1234")
        Long travelId,

        @JsonProperty("ticket_info") @Schema(description = "ticket information", example = "ticket info")
        TicketInfoDto ticketInfo,

        @JsonProperty("departure") @Schema(description = "location where to departure", example = "서울")
        String departure,

        @JsonProperty("arrive") @Schema(description = "location where to arrive", example = "전주")
        String arrive,

        @JsonProperty("keyword") @Schema(description = "travel types", example = "쇼핑, 관광")
        String keyword,

        @JsonProperty("start_date") @Schema(description = "travel start date", example = "2024-09-09")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate startDate,

        @JsonProperty("end_date") @Schema(description = "travel end date", example = "2024-09-13")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate endDate,

        @JsonProperty("members") @Schema(description = "travel members list", example = "[userTravelDto1, userTravelDto2]")
        List<GetUserTravelDto> members,

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