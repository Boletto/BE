package com.demoboletto.repository.travel;

import com.demoboletto.domain.Travel;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.demoboletto.domain.QTravel.travel;
import static com.demoboletto.domain.QUserTravel.userTravel;

@RequiredArgsConstructor
@Slf4j
public class TravelCustomRepositoryImpl implements TravelCustomRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<Travel> findTravelsByStartDate(LocalDate date) {
        return queryFactory.selectFrom(travel)
                .where(travel.startDate.eq(date))
                .fetch();
    }


    @Override
    public List<Travel> findTravelsByEndDate(LocalDate date) {
        return queryFactory.selectFrom(travel)
                .where(travel.endDate.eq(date))
                .fetch();
    }

    @Override
    public Optional<Travel> findByUserIdAndTravelId(Long userId, Long travelId) {
        return Optional.ofNullable(queryFactory.select(userTravel.travel)
                .from(userTravel)
                .where(userTravel.user.id.eq(userId)
                        .and(userTravel.travel.travelId.eq(travelId)))
                .fetchOne());
    }

    @Override
    public boolean existsAcceptedTravelByTravelDates(Long userId, LocalDate startDate, LocalDate endDate) {
        return queryFactory.selectOne()
                .from(userTravel)
                .where(
                        userTravel.accepted.isTrue(), // 수락된 여행
                        userTravel.user.id.eq(userId), // 특정 사용자 ID
                        userTravel.travel.startDate.loe(endDate), // 여행 시작 <= 주어진 종료
                        userTravel.travel.endDate.goe(startDate) // 여행 종료 >= 주어진 시작
                )
                .fetchFirst() != null;
    }
}
