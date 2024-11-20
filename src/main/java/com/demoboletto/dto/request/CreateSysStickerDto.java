package com.demoboletto.dto.request;

import com.demoboletto.domain.SysSticker;
import com.demoboletto.type.EStickerType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "시스템 스티커 생성")
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateSysStickerDto {

    @Schema(description = "스티커의 이름", example = "스티커1")
    @NotNull(message = "stickerName can not be null")
    private String stickerName;

    @Schema(description = "스티커 코드", example = "STICKER01")
    @NotNull(message = "stickerCode can not be null")
    private String stickerCode;

    @Schema(description = "스티커의 타입", example = "STICKER", defaultValue = "STICKER")
    @NotNull(message = "stickerType can not be null")
    @Enumerated(EnumType.STRING)
    private EStickerType stickerType;

    @Schema(description = "스티커의 기본 제공 여부", example = "true", defaultValue = "false")
    @NotNull(message = "defaultProvided can not be null")
    private boolean defaultProvided;

    @Schema(description = "스티커 설명", example = "스티커 설명입니다.")
    private String description;

    @Schema(description = "스티커 이미지 파일")
    @NotNull(message = "file can not be null")
    private MultipartFile file;
}
