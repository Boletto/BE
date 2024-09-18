package com.demoboletto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(name = "GetUserInfoDto", description = "Response DTO for user information")
public record GetUserInfoDto(
        @NotNull(message = "Name cannot be null")
        @JsonProperty("name") @Schema(description = "User's full name", example = "John Doe")
        String name,

        @NotNull(message = "Nickname cannot be null")
        @JsonProperty("nickname") @Schema(description = "User's nickname", example = "johnny")
        String nickname
) {
}
