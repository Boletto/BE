package com.demoboletto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 스티커 생성")
public class CreateUserStickerDto {
    @Schema(description = "스티커 코드")
    private String stickerCode;
}
