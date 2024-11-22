package com.demoboletto.repository;

import com.demoboletto.domain.TravelMemory;

import java.util.Optional;

public interface TravelMemoryCustomRepository {
    Optional<TravelMemory> findByTravelIdAndMemoryIdx(Long travelId, Long memoryIdx);
}
