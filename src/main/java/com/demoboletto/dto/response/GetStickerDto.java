package com.demoboletto.dto.response;

import com.demoboletto.type.ESticker;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(name = "GetStickerDto", description = "request for getting sticker list")
public record GetStickerDto(
        @NotNull(message = "travelId can not be null")
        @JsonProperty("travel_id") @Schema(description = "travel unique id", example = "1234")
        Long travelId,
        @NotNull(message = "stickerId can not be null")
        @JsonProperty("sticker_id") @Schema(description = "sticker unique id", example = "1234")
        Long stickerId,
        @NotNull(message = "locX can not be null")
        @JsonProperty("loc_x") @Schema(description = "sticker x location", example = "3.2")
        float locX,

        @NotNull(message = "locY can not be null")
        @JsonProperty("loc_y") @Schema(description = "sticker y location", example = "1.3")
        float locY,

        @NotNull(message = "field can not be null")
        @Enumerated(EnumType.STRING)
        @JsonProperty("field") @Schema(description = "sticker type info", example = "BASIC")
        ESticker field


) {
}