package com.demoboletto.repository;

import com.demoboletto.domain.SysSticker;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.demoboletto.domain.QSysSticker.sysSticker;

@RequiredArgsConstructor
public class SysStickerCustomRepositoryImpl implements SysStickerCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SysSticker> findSystemStickers() {
        return queryFactory
                .selectFrom(sysSticker)
                .where(sysSticker.isEvent.isFalse())
                .setHint("org.hibernate.cacheable", true)
                .setHint("org.hibernate.cacheRegion", "systemStickerRegion")
                .fetch();
    }

    @Override
    public List<SysSticker> findEventStickers() {
        LocalDate now = LocalDate.now();
        return queryFactory
                .selectFrom(sysSticker)
                .where(sysSticker.isEvent.isTrue())
                .where(sysSticker.eventStartDate.loe(now), sysSticker.eventEndDate.goe(now))
                .setHint("org.hibernate.cacheable", true)
                .setHint("org.hibernate.cacheRegion", "eventStickerRegion")
                .fetch();
    }

    @Override
    public List<SysSticker> findDefaultStickers() {
        return queryFactory
                .selectFrom(sysSticker)
                .where(sysSticker.defaultProvided.isTrue())
                .setHint("org.hibernate.cacheable", true)
                .setHint("org.hibernate.cacheRegion", "defaultStickerRegion")
                .fetch();
    }
}
