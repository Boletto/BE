package com.demoboletto.dto.response;

import com.demoboletto.domain.FriendCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@Schema(name = "FriendCodeDto", description = "Response Dto for Friend Code")
public class FriendCodeDto {
    @Schema(description = "Friend Code", example = "1234")
    @JsonProperty("friend_code")
    private String friendCode;

    @Schema(description = "User Nickname", example = "sunday")
    @JsonProperty("user_nickname")
    private String userNickname;

    @Schema(description = "Friend Code Expire Time", example = "2021-12-31")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("expire_date")
    private LocalDate expireDate;

    public static FriendCodeDto of(FriendCode friendCode) {
        return FriendCodeDto.builder()
                .friendCode(friendCode.getFriendCode())
                .userNickname(friendCode.getUser().getNickname())
                .expireDate(friendCode.getExpiredAt())
                .build();
    }
}
