package com.demoboletto.dto.response;

import com.demoboletto.domain.Picture;
import com.demoboletto.domain.Speech;
import com.demoboletto.domain.Sticker;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "GetPictureDto", description = "request for getting pictures list")
public record GetMemoryDto(
        @NotNull(message = "travelId can not be null")
        @JsonProperty("travel_id") @Schema(description = "travel unique id", example = "1234")
        Long travelId,
        @NotNull(message = "picture can not be null")
        @JsonProperty("picture") @Schema(description = "picture list", example = "[picture1, picture2]")
        List<Picture> pictures,
        @NotNull(message = "sticker can not be null")
        @JsonProperty("sticker") @Schema(description = "sticker list", example = "[sticker1, sticker2]")
        List<Sticker> stickers,
        @NotNull(message = "speech can not be null")
        @JsonProperty("speech") @Schema(description = "speech list", example = "[speech1, speech2]")
        List<Speech> speeches
) {
}