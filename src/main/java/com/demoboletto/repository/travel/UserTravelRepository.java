package com.demoboletto.repository.travel;

import com.demoboletto.domain.UserTravel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTravelRepository extends JpaRepository<UserTravel, Long>, UserTravelCustomRepository {
    List<UserTravel> findByUserId(Long userId);

    List<UserTravel> findUserTravelsByUserIdAndAccepted(Long userId, boolean isAccepted);
}
