package com.demoboletto.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.demoboletto.domain.QUser.user;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findDeviceTokensByUserIds(List<Long> userIds) {
        return queryFactory.select(user.deviceToken)
                .from(user)
                .where(user.id.in(userIds))
                .fetch();
    }

    @Override
    public List<String> findDeviceTokensByAllUsers() {
        return queryFactory.select(user.deviceToken)
                .from(user)
                .where(user.deviceToken.isNotNull())
                .fetch();
    }
}
