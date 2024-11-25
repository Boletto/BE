package com.demoboletto.dto.request;

import com.demoboletto.type.EStickerType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "시스템 스티커 생성")
@Getter
@Builder
public class CreateSysStickerDto {

    @Schema(description = "스티커의 이름", example = "스티커1")
    @JsonProperty("sticker_name")
    private String stickerName;

    @Schema(description = "스티커 코드", example = "STICKER01")
    @JsonProperty("sticker_code")
    private String stickerCode;

    @Schema(description = "스티커의 타입", example = "STICKER", defaultValue = "STICKER")
    @Enumerated(EnumType.STRING)
    @JsonProperty("sticker_type")
    private EStickerType stickerType;

    @Schema(description = "스티커의 기본 제공 여부", example = "true", defaultValue = "false")
    @JsonProperty("default_provided")
    private boolean defaultProvided;

    @Schema(description = "스티커 설명", example = "스티커 설명입니다.")
    private String description;

}
