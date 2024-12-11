package com.demoboletto.dto.push;

import com.demoboletto.dto.push.common.NotiDto;
import lombok.Builder;

import java.util.Map;

public class NotiFriendAcceptDto implements NotiDto {
    private final String senderNickName;

    @Builder
    public NotiFriendAcceptDto(String senderNickName) {
        this.senderNickName = senderNickName;
    }

    @Override
    public String getMessage() {
        return senderNickName + "님께서 친구 신청을 수락했어요! 이제 함께 볼레또를 즐겨보세요.";
    }

    @Override
    public String getTitle() {
        return "친구 신청 수락 ✉️";
    }

    @Override
    public Map<String, String> toMap() {
        return null;
    }
}
