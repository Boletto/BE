package com.demoboletto.repository.travel;

import com.demoboletto.domain.Travel;
import com.demoboletto.type.ETravelStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long>, TravelCustomRepository {
    @Query("select t.status from Travel t where t.travelId = :id")
    ETravelStatusType findStatusByTravelId(Long id);
}
