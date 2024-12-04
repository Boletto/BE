package com.demoboletto.repository.travel;

import com.demoboletto.domain.Travel;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserTravel;

import java.util.List;
import java.util.Optional;

public interface UserTravelCustomRepository {
    List<User> findUsersByTravelId(Long id);

    List<Travel> findTravelsByUserId(Long id);

    List<UserTravel> findAllByTravelId(Long travelId);

    void deleteAllByTravelId(Long travelId);

    List<String> findUserDeviceTokensByTravelId(Long travelId);

    Optional<UserTravel> findByUserIdAndTravelId(Long userId, Long travelId);

//    Optional<Travel> findByUserIdAndTravelId(Long userId, Long travelId);

}
