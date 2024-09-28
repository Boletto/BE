package com.demoboletto.repository;

import com.demoboletto.domain.Travel;
import com.demoboletto.type.EStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {
    @Query("select t.status from Travel t where t.travelId = :id")
    EStatusType findStatusByTravelId(Long id);
}
