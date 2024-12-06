package com.demoboletto.repository;

import com.demoboletto.domain.FriendCode;

import java.util.List;

public interface FriendCodeCustomRepository {
    List<FriendCode> findFriendCodesByUserId(Long userId);
}
