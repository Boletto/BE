package com.demoboletto.dto.push;

import com.demoboletto.dto.push.common.NotiDto;
import com.demoboletto.type.EAlarmType;
import lombok.Builder;

import java.util.Map;

/**
 * @author : hyunwoopark
 * @version : 1.0.0
 * @package : demo-boletto
 * @name : DispatchTravelInviteDto
 * @date : 2024. 12. 4. 오후 11:22
 * @modifyed : $
 **/
public class DispatchTravelInviteDto implements NotiDto {
    private final EAlarmType eventType;
    private final Long travelId;
    private final String value;

    @Builder
    public DispatchTravelInviteDto(EAlarmType eventType, Long travelId, String value) {
        this.eventType = eventType;
        this.travelId = travelId;
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getMessage() {
        return value + "님께서 여행에 초대했어요! 수락 후 함께 여행의 추억을 꾸며보세요.";
    }

    @Override
    public String getTitle() {
        return "여행 티켓 도착 ✉️";
    }

    @Override
    public Map<String, String> toMap() {
        return Map.of(
                "eventType", eventType.name(),
                "travelId", travelId.toString()
        );

    }
}
