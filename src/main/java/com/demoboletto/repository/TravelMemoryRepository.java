package com.demoboletto.repository;

import com.demoboletto.domain.Travel;
import com.demoboletto.domain.TravelMemory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelMemoryRepository extends JpaRepository<TravelMemory, Long>, TravelMemoryCustomRepository {
    Optional<TravelMemory> findByTravelAndMemoryIdx(Travel travel, Long memoryIdx);

    List<TravelMemory> findAllByTravel(Travel travel);
}
