package com.demoboletto.repository;

import com.demoboletto.domain.SysFrame;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

import static com.demoboletto.domain.QSysFrame.sysFrame;

@RequiredArgsConstructor
@Slf4j
public class SysFrameCustomRepositoryImpl implements SysFrameCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SysFrame> findSystemFrames() {
        return queryFactory.selectFrom(sysFrame)
                .where(sysFrame.isEvent.isFalse())
                .setHint("org.hibernate.cacheable", true)
                .setHint("org.hibernate.cacheRegion", "systemFrameRegion")
                .fetch();
    }

    @Override
    public List<SysFrame> findEventFrames() {
        LocalDate now = LocalDate.now();
        return queryFactory.selectFrom(sysFrame)
                .where(sysFrame.isEvent.isTrue())
                .where(sysFrame.eventStartDate.loe(now), sysFrame.eventEndDate.goe(now))
                .setHint("org.hibernate.cacheable", true)
                .setHint("org.hibernate.cacheRegion", "eventFrameRegion")
                .fetch();
    }

    @Override
    public List<SysFrame> findDefaultFrames() {
        return queryFactory.selectFrom(sysFrame)
                .where(sysFrame.defaultProvided.isTrue())
                .setHint("org.hibernate.cacheable", true)
                .setHint("org.hibernate.cacheRegion", "defaultFrameRegion")
                .fetch();
    }
}
