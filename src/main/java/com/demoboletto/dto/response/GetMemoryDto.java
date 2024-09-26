package com.demoboletto.dto.response;

import com.demoboletto.type.EStatusType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "GetMemoryDto", description = "request for getting memory list")
public record GetMemoryDto(
        @NotNull(message = "travelId can not be null")
        @JsonProperty("travel_id") @Schema(description = "travel unique id", example = "1234")
        Long travelId,
        @NotNull(message = "picture can not be null")
        @JsonProperty("picture_list") @Schema(description = "picture list", example = "[picture1, picture2]")
        List<GetPictureDto> pictureList,
        @NotNull(message = "sticker can not be null")
        @JsonProperty("sticker_list") @Schema(description = "sticker list", example = "[sticker1, sticker2]")
        List<GetStickerDto> stickerList,
        @NotNull(message = "speech can not be null")
        @JsonProperty("speech_list") @Schema(description = "speech list", example = "[speech1, speech2]")
        List<GetSpeechDto> speechList,
        @NotNull(message = "status can not be null")
        @Enumerated(EnumType.STRING)
        @JsonProperty("status") @Schema(description = "travel status", example = "UNLOCK")
        EStatusType status
) {
}