package com.demoboletto.dto.response;

import com.demoboletto.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(name = "GetUserTravelDto", description = "Response DTO for user travel information")
public record GetUserTravelDto(
        @NotNull(message = "Name cannot be null")
        @JsonProperty("name") @Schema(description = "User's full name", example = "John Doe")
        String name,

        @NotNull(message = "Nickname cannot be null")
        @JsonProperty("nickname") @Schema(description = "User's nickname", example = "johnny")
        String nickname,

        @NotNull(message = "userProfile cannot be null")
        @JsonProperty("user_profile") @Schema(description = "User's profile", example = "default")
        String userProfile,

        @NotNull(message = "userId can not be null")
        @JsonProperty("user_id") @Schema(description = "user unique id", example = "1234")
        Long userId
) {
}
