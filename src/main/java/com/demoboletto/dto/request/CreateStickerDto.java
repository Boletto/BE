package com.demoboletto.dto.request;

import com.demoboletto.type.ESticker;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(name = "CreateStickerDto", description = "request for creating sticker")
public record CreateStickerDto(
        @NotNull(message = "field can not be null")
        @Enumerated(EnumType.STRING)
        @JsonProperty("field") @Schema(description = "sticker field info", example = "HELLO")
        ESticker field,
        @NotNull(message = "locX can not be null")
        @JsonProperty("loc_x") @Schema(description = "speech location x", example = "0.0")
        float locX,
        @NotNull(message = "locY can not be null")
        @JsonProperty("loc_y") @Schema(description = "speech location y", example = "0.0")
        float locY,
        @NotNull(message = "rotation can not be null")
        @JsonProperty("rotation") @Schema(description = "speech rotation", example = "0")
        int rotation,
        @NotNull(message = "scale can not be null")
        @JsonProperty("scale") @Schema(description = "speech scale", example = "0")
        int scale
) {
}