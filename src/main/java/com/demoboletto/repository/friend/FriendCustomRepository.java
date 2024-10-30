package com.demoboletto.repository.friend;

import com.demoboletto.domain.Friend;

import java.util.List;

public interface FriendCustomRepository {
    List<Friend> findFriendByKeyword(String keyword);
}
