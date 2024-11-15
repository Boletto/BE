package com.demoboletto.dto.push;

import com.demoboletto.type.ETravelEventType;
import lombok.Builder;

import java.util.HashMap;
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
        Map<String, String> map = new HashMap<>();
        map.put("eventType", eventType.name());
        map.put("arriveArea", arriveArea);
        return map;
    }
}
