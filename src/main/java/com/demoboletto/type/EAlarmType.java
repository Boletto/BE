package com.demoboletto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EAlarmType {
    STICKER_ACQUISITION("{value} 스티커를 획득했어요."),
    REGION_ACTIVATION("'{value}' 네컷 제작이 활성화되었어요."),
    REGION_DEACTIVATION("'{value}' 네컷 제작이 비활성화되었어요."),
    TRAVEL_TICKET("{value} 님께서 여행에 초대했어요! 수락 후 함께 여행의 추억을 꾸며보세요."),
    FRIEND_INVITE_SENT("친구에게 초대를 전송했어요"),
    FRIEND_ACCEPT("{value} 님께서 친구 신청을 수락했어요! 이제 함께 볼레또를 즐겨보세요.");

    private final String template;

}
