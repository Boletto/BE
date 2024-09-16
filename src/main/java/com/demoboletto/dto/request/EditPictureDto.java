package com.demoboletto.dto.request;

import com.demoboletto.type.EStatusType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Schema(name = "EditPictureDto", description = "request for editing  picture")
public record EditPictureDto(
        @NotNull(message = "userId can not be null")
        @JsonProperty("user_id") @Schema(description = "user unique id", example = "1234")
        Long userId,

        @NotNull(message = "travelId can not be null")
        @JsonProperty("travel_id") @Schema(description = "travel unique id", example = "1234")
        Long travelId,
        @NotNull(message = "picture files can not be null")
        @Enumerated(EnumType.STRING)
        @JsonProperty("status") @Schema(description = "picture edit status", example = "LOCK")
        EStatusType status
) {
}