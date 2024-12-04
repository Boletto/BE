package com.demoboletto.repository.travel;

import com.demoboletto.domain.Travel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TravelCustomRepository {

    List<Travel> findTravelsByStartDate(LocalDate date);

    List<Travel> findTravelsByEndDate(LocalDate date);

    Optional<Travel> findByUserIdAndTravelId(Long userId, Long travelId);

    boolean existsByTravelDates(Long userId, LocalDate startDate, LocalDate endDate);
}
