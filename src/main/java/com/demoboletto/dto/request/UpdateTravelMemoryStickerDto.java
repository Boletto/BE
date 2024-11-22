package com.demoboletto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "UpdateTravelMemorySticker", description = "Update Travel Memory Sticker")
public class UpdateTravelMemoryStickerDto {

    @Schema(description = "System Sticker Code", example = "TT")
    private String stickerCode;

    @Schema(description = "Sticker location X", example = "0.0")
    private float locX;

    @Schema(description = "Sticker location Y", example = "0.0")
    private float locY;

    @Schema(description = "Sticker rotation", example = "0")
    private int rotation;

    @Schema(description = "Sticker scale", example = "1.0")
    private float scale;

    @Schema(description = "Sticker content(type eq speech)", example = "content")
    private String content;

}
