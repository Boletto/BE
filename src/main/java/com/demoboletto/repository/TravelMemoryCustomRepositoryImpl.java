package com.demoboletto.repository;

import com.demoboletto.domain.TravelMemory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.demoboletto.domain.QTravelMemory.travelMemory;

@RequiredArgsConstructor
public class TravelMemoryCustomRepositoryImpl implements TravelMemoryCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<TravelMemory> findByTravelIdAndMemoryIdx(Long travelId, Long memoryIdx) {
        return Optional.ofNullable(queryFactory.selectFrom(travelMemory)
                .where(travelMemory.travel.travelId.eq(travelId)
                        .and(travelMemory.memoryIdx.eq(memoryIdx)))
                .fetchFirst());
    }
}
