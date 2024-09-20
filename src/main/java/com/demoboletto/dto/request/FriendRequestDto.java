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
        Long friendUserId,

        @NotNull(message = "friendName can not be null")
        @JsonProperty("friendName") @Schema(description = "Name of the friend", example = "홍길동")
        String friendName,

        @NotNull(message = "friendNickname can not be null")
        @JsonProperty("friendNickname") @Schema(description = "Nickname of the friend", example = "길동이")
        String friendNickname,

        @NotNull(message = "friendProfile can not be null")
        @JsonProperty("friendProfile") @Schema(description = "Profile of the friend (image URL or enum value)", example = "CUSTOM")
        String friendProfile
) {
}

