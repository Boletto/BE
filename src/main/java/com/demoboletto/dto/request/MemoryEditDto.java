package com.demoboletto.dto.request;

import com.demoboletto.type.EStatusType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "MemoryEditDto", description = "request for editing memory")
public record MemoryEditDto(
        @NotNull(message = "travelId can not be null")
        @JsonProperty("travel_id") @Schema(description = "travel unique id", example = "1234")
        Long travelId,
        @NotNull(message = "picture files can not be null")
        @Enumerated(EnumType.STRING)
        @JsonProperty("status") @Schema(description = "picture edit status", example = "LOCK")
        EStatusType status,
        @JsonProperty("sticker_list") @Schema(description = "sticker list", example = "[sticker1, sticker2]")
        List<CreateStickerDto> stickerList,
        @JsonProperty("speech_list") @Schema(description = "speech list", example = "[speech1, speech2]")
        List<CreateSpeechDto> speechList


) {
}
