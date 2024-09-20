package com.demoboletto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(description = "Response for add friend")
public record AddFriendResponseDto (
        @NotNull
        @JsonProperty("userId")
        @Schema(description = "친구를 추가하려고 하는 유저의 아이디", example = "1")
        Long userId,

        @JsonProperty("userName")
        @Schema(description = "친구를 추가하려고 하는 유저의 이름", example = "leeeunda")
        String userName,

        @JsonProperty("userNickName")
        @Schema(description = "친구를 추가하려고 하는 유저의 닉네임", example = "leeeunda")
        String userNickName,

        @JsonProperty("userProfile")
        @Schema(description = "친구를 추가하려고 하는 유저의 프로필 이미지", example = "leeeunda")
        String userProfile,

        @JsonProperty("friendUserId")
        @Schema(description = "추가하고자 하는 친구의 유저 아이디", example = "2")
        Long friendUserId,

        @JsonProperty("friendName")
        @Schema(description = "추가하고자 하는 친구의 닉네임", example = "Jonny")
        String friendName,

        @JsonProperty("friendNickname")
        @Schema(description = "추가하고자 하는 친구의 이름", example = "Jonny Doe")
        String friendNickName,

        @JsonProperty("friendProfile")
        @Schema(description = "추가하고자 하는 친구의 프로필", example = "picture.url")
        String friendProfile

)
{
}
