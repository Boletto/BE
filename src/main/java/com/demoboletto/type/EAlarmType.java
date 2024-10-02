package com.demoboletto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EAlarmType {
    STICKER_ACQUISITION("{stickerName} 스티커를 획득했어요."),
    REGION_ACTIVATION("'{regionName}' 네컷 제작이 활성화되었어요."),
    REGION_DEACTIVATION("'{regionName}' 네컷 제작이 비활성화되었어요."),
    TRAVEL_TICKET("초대받은 여행 티켓이 {ticketCount}개 있어요."),
    FRIEND_INVITE_SENT("친구에게 초대를 전송했어요");

    private final String template;
}
