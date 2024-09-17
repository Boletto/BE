package com.demoboletto.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Schema(name = "CreatePictureDto", description = "request for saving  picture")
public record CreatePictureDto(
        @NotNull(message = "userId can not be null")
        @JsonProperty("user_id") @Schema(description = "user unique id", example = "1234")
        Long userId,

        @NotNull(message = "travelId can not be null")
        @JsonProperty("travel_id") @Schema(description = "travel unique id", example = "1234")
        Long travelId,
        @NotNull(message = "picture files can not be null")
        @JsonProperty("picture_file") @Schema(description = "picture files list", example = "files1")
        MultipartFile pictureFile,
        @NotNull(message = "picture index can not be null")
        @JsonProperty("picture_idx") @Schema(description = "picture location index", example = "0")
        int pictureIdx
) {
}