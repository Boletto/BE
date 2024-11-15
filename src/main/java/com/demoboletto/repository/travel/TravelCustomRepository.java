package com.demoboletto.repository.travel;

import com.demoboletto.domain.Travel;

import java.time.LocalDate;
import java.util.List;

public interface TravelCustomRepository {

    List<Travel> findTravelsByStartDate(LocalDate date);

    List<Travel> findTravelsByEndDate(LocalDate date);
}
