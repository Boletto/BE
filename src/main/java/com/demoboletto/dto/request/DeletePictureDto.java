package com.demoboletto.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
@Schema(name = "DeletePictureDto", description = "request for deleting  picture")
public record DeletePictureDto(
        @NotNull(message = "travelId can not be null")
        @JsonProperty("travel_id") @Schema(description = "travel unique id", example = "1234")
        Long travelId,
        @NotNull(message = "picture index can not be null")
        @JsonProperty("picture_idx") @Schema(description = "picture location index", example = "0")
        int pictureIdx,
        @NotNull(message = "picture isFourCut can not be null")
        @JsonProperty("is_fourCut") @Schema(description = "whether picture is fourCut", example = "false")
        boolean isFourCut

) {
}