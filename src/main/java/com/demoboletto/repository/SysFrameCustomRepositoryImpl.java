package com.demoboletto.repository;

import com.demoboletto.domain.SysFrame;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.demoboletto.domain.QSysFrame.sysFrame;

@RequiredArgsConstructor
@Slf4j
public class SysFrameCustomRepositoryImpl implements SysFrameCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SysFrame> findDefaultFrames() {
        return queryFactory.selectFrom(sysFrame)
                .where(sysFrame.defaultProvided.isTrue())
                .fetch();
    }
}
