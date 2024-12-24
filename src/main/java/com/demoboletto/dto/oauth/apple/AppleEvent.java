package com.demoboletto.dto.oauth.apple;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AppleEvent {
    AppleEventType type;
    String sub;
    @JsonProperty("event_time")
    long eventTime;
}
