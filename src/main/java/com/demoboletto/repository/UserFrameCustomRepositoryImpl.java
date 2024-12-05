package com.demoboletto.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.demoboletto.domain.QUserFrame.userFrame;

@RequiredArgsConstructor
@Slf4j
public class UserFrameCustomRepositoryImpl implements UserFrameCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteUserFramesByUserId(Long userId) {
        queryFactory
                .delete(userFrame)
                .where(userFrame.user.id.eq(userId))
                .execute();
    }
}
