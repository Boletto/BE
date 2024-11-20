package com.demoboletto.dto.response;

import com.demoboletto.domain.SysSticker;
import com.demoboletto.domain.UserSticker;
import com.demoboletto.dto.global.BaseTimeDto;
import com.demoboletto.type.EStickerType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Schema(description = "스티커 정보")
@Getter
@SuperBuilder
public class GetUserStickerInfoDto extends BaseTimeDto {
    @Schema(description = "스티커의 기본키", example = "1")
    private Long stickerId;

    @Schema(description = "스티커의 코드", example = "STICKER01")
    private String stickerCode;

    @Schema(description = "스티커의 이름", example = "스티커1")
    private String stickerName;

    @Schema(description = "스티커의 타입", example = "STICKER OR SPEECH")
    private EStickerType stickerType;

    @Schema(description = "스티커의 소유 여부", example = "true")
    private boolean isOwned;

    @Schema(description = "스티커 이미지 URL", example = "https://example.com/sticker.jpg")
    private String stickerUrl;

    @Schema(description = "스티커 설명", example = "스티커 설명입니다.")
    private String description;

//    public static GetUserStickerInfoDto of(UserSticker userSticker) {
//        return GetUserStickerInfoDto.builder()
//                .stickerId(userSticker.getSticker().getStickerId())
//                .stickerCode(userSticker.getSticker().getStickerCode())
//                .stickerName(userSticker.getSticker().getStickerName())
//                .stickerType(userSticker.getSticker().getStickerType())
//                .isOwned(true)
//                .stickerUrl(userSticker.getSticker().getStickerUrl())
//                .description(userSticker.getSticker().getDescription())
//                .createdDate(userSticker.getCreatedDate())
//                .modifiedDate(userSticker.getModifiedDate())
//                .build();
//    }

}
