package com.demoboletto.dto.push;

import com.demoboletto.dto.push.common.NotiDto;
import com.demoboletto.type.EAlarmType;
import lombok.Builder;

import java.util.Map;

public class NotiTravelStartDto implements NotiDto {
    private final String value;
    private final EAlarmType eventType;

    @Builder
    public NotiTravelStartDto(String value, EAlarmType eventType) {
        this.value = value;
        this.eventType = eventType;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getMessage() {
        return "계획된 여행이 시작되었어요. 볼레또에서 여행을 함께해요!";
    }

    @Override
    public String getTitle() {
        return "여행 시작 ✉️";
    }

    @Override
    public Map<String, String> toMap() {
        return Map.of(
                "eventType", eventType.name()
        );

    }
}
