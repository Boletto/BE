package com.demoboletto.repository;

import com.demoboletto.domain.Travel;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserTravel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTravelRepository extends JpaRepository<UserTravel, Long> {
    @Query("select ut.user from UserTravel ut where ut.travel.travelId = :id")
    List<User> findAllByTravelId(Long id);
    @Query("select ut.travel from UserTravel ut where ut.user.id = :id")
    List<Travel> findAllByUserId(Long id);
}
