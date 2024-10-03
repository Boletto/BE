package com.demoboletto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Response for friend's information")
public record FriendResponseDto(

        @JsonProperty("userId")
        @Schema(description = "ID of the friend user", example = "2")
        Long friendUserId,

        @JsonProperty("nickName")
        @Schema(description = "Nickname of the friend", example = "으아어")
        String friendNickname,

        @JsonProperty("name")
        @Schema(description = "Name of the friend", example = "홍길동")
        String friendName,

        @JsonProperty("userProfile")
        @Schema(description = "Profile of the friend", example = "BLUE")
        String friendProfile
) {
}