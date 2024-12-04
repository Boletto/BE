package com.demoboletto.dto.push;

import com.demoboletto.type.ETravelEventType;
import lombok.Builder;

import java.util.Map;

public class DispatchTravelEventDto {
    public ETravelEventType eventType;
    public String arriveArea;

    @Builder
    public DispatchTravelEventDto(ETravelEventType eventType, String arriveArea) {
        this.eventType = eventType;
        this.arriveArea = arriveArea;
    }

    public Map<String, String> toMap() {
        return Map.of(
                "eventType", eventType.name(),
                "arriveArea", arriveArea
        );
    }
}
