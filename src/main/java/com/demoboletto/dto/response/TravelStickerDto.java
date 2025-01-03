package com.demoboletto.dto.response;

import com.demoboletto.domain.TravelSticker;
import com.demoboletto.type.EStickerType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TravelStickerDto {
    private String stickerCode;

    private EStickerType stickerType;

    private String stickerUrl;

    private String locX;

    private String locY;

    private int rotation;

    private float scale;

    private String content;

    public static TravelStickerDto of(TravelSticker travelSticker) {
        return TravelStickerDto.builder()
                .stickerCode(travelSticker.getSysSticker().getStickerCode())
                .stickerType(travelSticker.getSysSticker().getStickerType())
                .stickerUrl(travelSticker.getSysSticker().getStickerUrl())
                .locX(String.format("%.2f", travelSticker.getLocX()))  // locX 소수점 2자리로 포맷
                .locY(String.format("%.2f", travelSticker.getLocY()))
                .rotation(travelSticker.getRotation())
                .scale(travelSticker.getScale())
                .content(travelSticker.getContent())
                .build();
    }
}