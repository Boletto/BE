package com.demoboletto.dto.push;

import com.demoboletto.dto.push.common.NotiDto;
import com.demoboletto.type.EAlarmType;
import lombok.Builder;

import java.util.Map;

public class NotiFriendAcceptDto implements NotiDto {
    private final String value;
    private final EAlarmType eventType;

    @Builder
    public NotiFriendAcceptDto(String value, EAlarmType eventType) {
        this.value = value;
        this.eventType = eventType;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getMessage() {
        return value + "님께서 친구 신청을 수락했어요! 이제 함께 볼레또를 즐겨보세요.";
    }

    @Override
    public String getTitle() {
        return "친구 신청 수락 ✉️";
    }

    @Override
    public Map<String, String> toMap() {
        return Map.of(
                "eventType", eventType.name()
        );

    }
}
