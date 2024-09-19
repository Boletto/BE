package com.demoboletto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Response for friend's information")
public record FriendResponseDto(

        @JsonProperty("friend_user_id")
        @Schema(description = "ID of the friend user", example = "2")
        Long friendUserId,

        @JsonProperty("friend_nickname")
        @Schema(description = "Nickname of the friend", example = "으아어")
        String friendNickname,

        @JsonProperty("friend_name")
        @Schema(description = "Name of the friend", example = "홍길동")
        String friendName,

        @JsonProperty("friend_profile")
        @Schema(description = "Profile of the friend", example = "BLUE")
        String friendProfile
) {
}