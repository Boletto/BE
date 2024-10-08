package com.demoboletto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "GetPictureDto", description = "request for getting picture")
public record GetPictureDto(
        @NotNull(message = "pictureId can not be null")
        @JsonProperty("picture_id") @Schema(description = "picture unique id", example = "1234")
        Long pictureId,
        @NotNull(message = "picture url can not be null")
        @JsonProperty("picture_url") @Schema(description = "picture url", example = "https://boletto.com/abc.jpg")
        String pictureUrl,
        @NotNull(message = "picture index can not be null")
        @JsonProperty("picture_idx") @Schema(description = "picture location index", example = "0")
        int pictureIdx
) {
}