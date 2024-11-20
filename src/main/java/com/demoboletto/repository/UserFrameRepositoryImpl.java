package com.demoboletto.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class UserFrameRepositoryImpl implements UserFrameCustomRepository {
    private final JPAQueryFactory queryFactory;

}
