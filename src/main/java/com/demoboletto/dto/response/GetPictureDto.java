package com.demoboletto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "GetPictureDto", description = "request for getting pictures list")
public record GetPictureDto(
        @NotNull(message = "travelId can not be null")
        @JsonProperty("travelId") @Schema(description = "travel unique id", example = "1234")
        Long travelId,
        @NotNull(message = "pictureUrl can not be null")
        @JsonProperty("pictureUrl") @Schema(description = "picture location url", example = "['http://www.boletto.com/picture.jpg']")
        List<String> pictureUrl,
        @NotNull(message = "arrive can not be null")
        @JsonProperty("pictureIdx") @Schema(description = "picture location index", example = "[0]")
        List<Integer> pictureIdx
) {
}