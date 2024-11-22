package com.demoboletto.dto.temp;

import com.demoboletto.domain.TravelMemory;
import com.demoboletto.domain.TravelSticker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "get travel memory")
public class GetTravelMemoryDto {
    @Schema(description = "travel memory list")
    private final List<TravelMemoryDto> memories;

    @Schema(description = "travel sticker list")
    private final List<TravelStickerDto> stickers;

    @Builder
    public GetTravelMemoryDto(List<TravelMemory> memories, List<TravelSticker> stickers) {
        this.memories = memories.stream().map(TravelMemoryDto::of).toList();
        this.stickers = stickers.stream().map(TravelStickerDto::of).toList();
    }
}


