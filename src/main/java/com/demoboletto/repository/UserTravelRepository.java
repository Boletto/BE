package com.demoboletto.repository;

import com.demoboletto.domain.UserTravel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTravelRepository extends JpaRepository<UserTravel, Long> {
}
