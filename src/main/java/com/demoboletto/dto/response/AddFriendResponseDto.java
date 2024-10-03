package com.demoboletto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(description = "Response for add friend")
public record AddFriendResponseDto (

        @JsonProperty("userId")
        @Schema(description = "추가하고자 하는 친구의 유저 아이디", example = "2")
        Long friendUserId,

        @JsonProperty("name")
        @Schema(description = "추가하고자 하는 친구의 닉네임", example = "Jonny")
        String friendNickName,

        @JsonProperty("nickName")
        @Schema(description = "추가하고자 하는 친구의 이름", example = "Jonny Doe")
        String friendName,

        @JsonProperty("userProfile")
        @Schema(description = "추가하고자 하는 친구의 프로필", example = "picture.url")
        String friendProfile

)
{
}
