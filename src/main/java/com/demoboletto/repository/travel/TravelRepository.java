package com.demoboletto.repository.travel;

import com.demoboletto.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long>, TravelCustomRepository {
}
