package com.demoboletto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(name="GetUserProfileUpdateDto", description = "Response Dto for User Profile Update")
public record GetUserProfileUpdateDto(
        @JsonProperty("nickname") @Schema(description = "User's nickname", example = "johnny")
        String nickname,

        @JsonProperty("name") @Schema(description = "User's name", example = "이다은")
        String name,

        @JsonProperty("profileUrl") @Schema(description = "User's Profile url", example = "img.png")
        String profileUrl
        ) {
}
