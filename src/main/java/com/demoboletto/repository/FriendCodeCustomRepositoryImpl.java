package com.demoboletto.repository;

import com.demoboletto.domain.FriendCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.demoboletto.domain.QFriendCode.friendCode1;

@RequiredArgsConstructor
@Slf4j
public class FriendCodeCustomRepositoryImpl implements FriendCodeCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<FriendCode> findFriendCodesByUserId(Long userId) {
        return queryFactory.selectFrom(friendCode1)
                .where(friendCode1.user.id.eq(userId))
                .fetch();
    }
}
