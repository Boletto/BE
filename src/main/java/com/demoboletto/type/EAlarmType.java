package com.demoboletto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EAlarmType {
    STICKER_ACQUISITION,
    REGION_ACTIVATION,
    REGION_DEACTIVATION,
    TRAVEL_TICKET,
    FRIEND_INVITE_SENT,
    FRIEND_ACCEPT,
}
