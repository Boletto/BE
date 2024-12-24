package com.demoboletto.dto.oauth.apple;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AppleEventType {
    CONSENT_REVOKED("consent-revoked"), ACCOUNT_DELETE("account-delete");

    private final String value;

    AppleEventType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static AppleEventType forValue(String value) {
        for (AppleEventType type : AppleEventType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
