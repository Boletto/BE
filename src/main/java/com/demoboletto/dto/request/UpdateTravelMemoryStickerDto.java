package com.demoboletto.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "UpdateTravelMemorySticker", description = "Update Travel Memory Sticker")
public class UpdateTravelMemoryStickerDto {

    @Schema(description = "System Sticker Code", example = "TT")
    @JsonProperty("sticker_code")
    private String stickerCode;

    @Schema(description = "Sticker location X", example = "0.0")
    @JsonProperty("loc_x")
    private float locX;

    @Schema(description = "Sticker location Y", example = "0.0")
    @JsonProperty("loc_y")
    private float locY;

    @Schema(description = "Sticker rotation", example = "0")
    @JsonProperty("rotation")
    private int rotation;

    @Schema(description = "Sticker scale", example = "1.0")
    @JsonProperty("scale")
    private float scale;

    @Schema(description = "Sticker content(type eq speech)", example = "content")
    @JsonProperty("content")
    private String content;

}
