package com.demoboletto.dto.response;

import com.demoboletto.domain.Picture;
import com.demoboletto.domain.TravelMemory;
import com.demoboletto.type.EMemoryType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TravelMemoryDto {
    @Enumerated(value = EnumType.STRING)
    private EMemoryType memoryType;

    private String frameUrl;

    private List<String> pictures;

    private Long memoryIdx;

    public static TravelMemoryDto of(TravelMemory travelMemory) {
        return TravelMemoryDto.builder()
                .memoryType(travelMemory.getMemoryType())
                .frameUrl(travelMemory.getFrameUrl())
                .pictures(travelMemory.getPictures().stream()
                        .map(Picture::getPictureUrl)
                        .toList()
                )
                .memoryIdx(travelMemory.getMemoryIdx())
                .build();
    }
}