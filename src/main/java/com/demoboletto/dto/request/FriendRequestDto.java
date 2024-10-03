package com.demoboletto.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(name = "FriendRequestDto", description = "Request for adding a new friend")
public record FriendRequestDto(
        @NotNull(message = "userId can not be null")
        @JsonProperty("userId") @Schema(description = "ID of the user adding the friend", example = "1")
        Long userId,

        @NotNull(message = "friendUserId can not be null")
        @JsonProperty("friendUserId") @Schema(description = "ID of the user being added as friend", example = "2")
        Long friendUserId
) {
}

