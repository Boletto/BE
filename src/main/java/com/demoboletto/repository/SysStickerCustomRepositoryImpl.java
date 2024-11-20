package com.demoboletto.repository;

import com.demoboletto.domain.SysSticker;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.demoboletto.domain.QSysSticker.sysSticker;

@RequiredArgsConstructor
public class SysStickerCustomRepositoryImpl implements SysStickerCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SysSticker> findDefaultStickers() {
        return queryFactory
                .selectFrom(sysSticker)
                .where(sysSticker.defaultProvided.isTrue())
                .fetch();
    }
}
