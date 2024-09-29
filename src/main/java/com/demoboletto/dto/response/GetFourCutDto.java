package com.demoboletto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "GetFourCutDto", description = "request for getting fourCut picture")
public record GetFourCutDto(
        @JsonProperty("picture_id") @Schema(description = "picture unique id", example = "[1234]")
        List<Long> pictureId,
        @JsonProperty("picture_url") @Schema(description = "picture url", example = "[https://boletto.com/abc.jpg]")
        List<String> pictureUrl,
        @NotNull(message = "picture index can not be null")
        @JsonProperty("picture_idx") @Schema(description = "picture location index", example = "0")
        int pictureIdx,
        @NotNull(message = "fourCutId can not be null")
        @JsonProperty("fourCut_id") @Schema(description = "fourCut unique id", example = "123")
        Long fourCutId,
        @NotNull(message = "collectId can not be null")
        @JsonProperty("collect_id") @Schema(description = "collect unique id", example = "123")
        Long collectId,
        @NotNull(message = "frame_type can not be null")
        @JsonProperty("frame_type") @Schema(description = "frameUrl", example = "https://boletto.com/abc.jpg")
        String frameUrl
) {
}