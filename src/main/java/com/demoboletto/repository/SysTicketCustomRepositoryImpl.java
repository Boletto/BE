package com.demoboletto.repository;

import com.demoboletto.domain.SysTicket;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

import static com.demoboletto.domain.QSysTicket.sysTicket;

@RequiredArgsConstructor
public class SysTicketCustomRepositoryImpl implements SysTicketCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<SysTicket> randomEventTicket() {
        LocalDate now = LocalDate.now();
        return Optional.ofNullable(queryFactory.selectFrom(sysTicket)
                .where(sysTicket.defaultProvided.isFalse())
                .where(
                        sysTicket.eventStartDate.loe(now),
                        sysTicket.eventEndDate.goe(now)
                )
                .orderBy(Expressions.numberTemplate(Double.class, "DBMS_RANDOM.VALUE()").asc())
                .fetchFirst());
    }

    @Override
    public Optional<SysTicket> randomTicket() {
        return Optional.ofNullable(queryFactory.selectFrom(sysTicket)
                .where(sysTicket.defaultProvided.isTrue())
                .orderBy(Expressions.numberTemplate(Double.class, "DBMS_RANDOM.VALUE()").asc())
                .fetchFirst());
    }
}
