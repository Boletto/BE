package com.demoboletto.dto.response;

import com.demoboletto.domain.SysSticker;
import com.demoboletto.domain.UserSticker;
import com.demoboletto.dto.global.BaseTimeDto;
import com.demoboletto.type.EStickerType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Schema(description = "Usable sticker information")
@Getter
@SuperBuilder
public class GetUserUsableStickerDto extends BaseTimeDto {
    @Schema(description = "스티커 기본키", example = "1")
    private Long stickerId;

    @Schema(description = "스티커 코드", example = "STICKER01")
    private String stickerCode;

    @Schema(description = "스티커 이름", example = "스티커1")
    private String stickerName;

    @Schema(description = "스티커 타입", example = "STICKER OR SPEECH")
    @Enumerated(EnumType.STRING)
    private EStickerType stickerType;

    @Schema(description = "스티커 이미지 URL", example = "https://example.com/sticker.jpg")
    private String stickerUrl;

    @Schema(description = "스티커 설명", example = "스티커 설명입니다.")
    private String description;

    @Schema(description = "스티커 소유 여부", example = "true")
    private boolean isOwned;

    public static GetUserUsableStickerDto of(SysSticker sysSticker) {
        return GetUserUsableStickerDto.builder()
                .stickerId(sysSticker.getStickerId())
                .stickerCode(sysSticker.getStickerCode())
                .stickerName(sysSticker.getStickerName())
                .stickerType(sysSticker.getStickerType())
                .stickerUrl(sysSticker.getStickerUrl())
                .description(sysSticker.getDescription())
                .isOwned(false)
                .build();
    }

    public static GetUserUsableStickerDto of(UserSticker userSticker) {
        return GetUserUsableStickerDto.builder()
                .stickerId(userSticker.getSticker().getStickerId())
                .stickerCode(userSticker.getSticker().getStickerCode())
                .stickerName(userSticker.getSticker().getStickerName())
                .stickerType(userSticker.getSticker().getStickerType())
                .stickerUrl(userSticker.getSticker().getStickerUrl())
                .description(userSticker.getSticker().getDescription())
                .isOwned(true)
                .createdDate(userSticker.getCreatedDate())
                .modifiedDate(userSticker.getModifiedDate())
                .build();
    }
}
