package com.demoboletto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EFriendType {
    ACTIVATE, // 서비스에 회원가입 되어 있는 상태
    NOT_ACTIVATE; // 서비스에 회원가입 되어있지 않은 상태
}
