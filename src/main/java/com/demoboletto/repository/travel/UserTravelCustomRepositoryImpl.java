package com.demoboletto.repository.travel;

import com.demoboletto.domain.Travel;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserTravel;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.demoboletto.domain.QUserTravel.userTravel;

@RequiredArgsConstructor
public class UserTravelCustomRepositoryImpl implements UserTravelCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> findUsersByTravelId(Long id) {
        return queryFactory.select(userTravel.user)
                .from(userTravel)
                .where(userTravel.travel.travelId.eq(id))
                .fetch();
    }

    @Override
    public List<Travel> findTravelsByUserId(Long id) {
        return queryFactory.select(userTravel.travel)
                .from(userTravel)
                .where(userTravel.user.id.eq(id))
                .fetch();
    }

    @Override
    public List<UserTravel> findAllByTravelId(Long travelId) {
        return queryFactory.selectFrom(userTravel)
                .where(userTravel.travel.travelId.eq(travelId))
                .fetch();
    }

    @Override
    public void deleteAllByTravelId(Long travelId) {
        queryFactory.delete(userTravel)
                .where(userTravel.travel.travelId.eq(travelId))
                .execute();
    }

    @Override
    public List<String> findUserDeviceTokensByTravelId(Long travelId) {
        return queryFactory.select(userTravel.user.deviceToken)
                .from(userTravel)
                .where(userTravel.travel.travelId.eq(travelId))
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
}
