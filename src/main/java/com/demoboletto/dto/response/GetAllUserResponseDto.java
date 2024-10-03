package com.demoboletto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Response for get All Users information")
public record GetAllUserResponseDto(
        @JsonProperty("userId")
        @Schema(description = "ID of the friend user", example = "2")
        Long userId,

        @JsonProperty("nickName")
        @Schema(description = "Nickname of the friend", example = "으아어")
        String nickName,

        @JsonProperty("name")
        @Schema(description = "Name of the friend", example = "홍길동")
        String name,

        @JsonProperty("userProfile")
        @Schema(description = "Profile of the friend", example = "BLUE")
        String userProfile
) {
}