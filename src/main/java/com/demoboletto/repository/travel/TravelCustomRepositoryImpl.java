package com.demoboletto.repository.travel;

import com.demoboletto.domain.Travel;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

import static com.demoboletto.domain.QTravel.travel;

@RequiredArgsConstructor
@Slf4j
public class TravelCustomRepositoryImpl implements TravelCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findTravelIdsByStartDate(LocalDate date) {
        return queryFactory.select(travel.travelId)
                .from(travel)
                .where(travel.startDate.eq(date))
                .fetch();
    }

    @Override
    public List<Travel> findTravelsByStartDate(LocalDate date) {
        return queryFactory.selectFrom(travel)
                .where(travel.startDate.eq(date))
                .fetch();
    }

    @Override
    public List<Long> findTravelIdsByEndDate(LocalDate date) {
        return queryFactory.select(travel.travelId)
                .from(travel)
                .where(travel.endDate.eq(date))
                .fetch();
    }

    @Override
    public List<Travel> findTravelsByEndDate(LocalDate date) {
        return queryFactory.selectFrom(travel)
                .where(travel.endDate.eq(date))
                .fetch();
    }
}
