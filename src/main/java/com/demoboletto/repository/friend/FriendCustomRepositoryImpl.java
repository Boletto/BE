package com.demoboletto.repository.friend;

import com.demoboletto.domain.Friend;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.demoboletto.domain.QFriend.friend;

@RequiredArgsConstructor
@Slf4j
public class FriendCustomRepositoryImpl implements FriendCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Friend> findFriendByKeyword(Long userId, String keyword) {
        return queryFactory.select(friend)
                .where(
                        friend.user.id.eq(userId),
                        friend.friendName.contains(keyword)
                                .or(friend.friendNickname.contains(keyword)))
                .from(friend)
                .fetch();
    }

    @Override
    public void deleteByFriendUserId(Long userId, Long friendId) {
        queryFactory.delete(friend)
                .where(friend.user.id.eq(userId)
                        .and(friend.friendUser.id.eq(friendId)))
                .execute();
        queryFactory.delete(friend)
                .where(friend.user.id.eq(friendId)
                        .and(friend.friendUser.id.eq(userId)))
                .execute();
    }
}
