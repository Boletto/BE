package com.demoboletto.dto.push;

import com.demoboletto.type.ESilentEventType;
import lombok.Builder;

import java.util.Map;

public class DispatchSystemEventDto {
    public ESilentEventType eventType;

    @Builder
    public DispatchSystemEventDto(ESilentEventType eventType) {
        this.eventType = eventType;
    }

    public Map<String, String> toMap() {
        return Map.of(
                "eventType", eventType.name()
        );
    }
}
