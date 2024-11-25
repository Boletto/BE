package com.demoboletto.dto.request;

import com.demoboletto.type.ETravelStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "UpdateTravelStatusDto", description = "Update Travel Status")
@Getter
@Builder
public class UpdateTravelStatusDto {

    @Schema(description = "Travel Status", example = "LOCK")
    @Enumerated(EnumType.STRING)
    private ETravelStatusType status;
}
