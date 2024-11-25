package com.demoboletto.dto.request;

import com.demoboletto.type.EMemoryType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "Index 기반 여행의 Memory 수정")
@Builder
public class UpdateTravelEachMemoryDto {
    @Schema(description = "Memory 타입", example = "PICTURE")
    @JsonProperty("memory_type")
    @Enumerated(EnumType.STRING)
    private EMemoryType memoryType;

    @Schema(description = "Frame Code", example = "TT")
    @JsonProperty("frame_code")
    private String frameCode;

}