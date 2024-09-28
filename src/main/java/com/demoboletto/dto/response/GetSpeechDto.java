package com.demoboletto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(name = "GetSpeechDto", description = "request for getting speech")
public record GetSpeechDto(
        @NotNull(message = "speechId can not be null")
        @JsonProperty("speech_id") @Schema(description = "speech unique id", example = "1234")
        Long speechId,
        @NotNull(message = "text can not be null")
        @JsonProperty("text") @Schema(description = "speech text", example = "hello")
        String text,
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
        float scale
) {
}