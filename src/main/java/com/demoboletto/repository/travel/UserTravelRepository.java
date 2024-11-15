package com.demoboletto.repository.travel;

import com.demoboletto.domain.Travel;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserTravel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTravelRepository extends JpaRepository<UserTravel, Long>, UserTravelCustomRepository {
    List<UserTravel> findByUserId(Long userId);
}
