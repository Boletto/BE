package com.demoboletto.dto.push;

import com.demoboletto.type.ETravelEventType;
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
public class DispatchTravelInviteDto {
    private final ETravelEventType eventType;
    private final Long travelId;
    private final String senderNickName;

    @Builder
    public DispatchTravelInviteDto(ETravelEventType eventType, Long travelId, String senderNickName) {
        this.eventType = eventType;
        this.travelId = travelId;
        this.senderNickName = senderNickName;
    }

    public String getMessage() {
        return senderNickName + "님께서 여행에 초대했어요! 수락 후 함께 여행의 추억을 꾸며보세요.";
    }

    public String getTitle() {
        return "여행 티켓 도착 ✉️";
    }

    public Map<String, String> toMap() {
        return Map.of(
                "eventType", eventType.name(),
                "travelId", travelId.toString()
        );

    }
}
