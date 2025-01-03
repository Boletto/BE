package com.demoboletto.dto.request;

import com.demoboletto.type.EAlarmType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

@Builder
public record RecordUserAlarmDto(
        @Enumerated(EnumType.STRING)
        @JsonProperty("alarm_type")
        @Schema(description = "alarm type", example = "STICKER_ACQUISITION")
        EAlarmType alarmType,

        @JsonProperty("value")
        @Schema(description = "value", example = "{value} 스티커를 획득했어요.")
        String value
) {
}
