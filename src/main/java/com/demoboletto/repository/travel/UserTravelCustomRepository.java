package com.demoboletto.repository.travel;

import com.demoboletto.domain.Travel;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserTravel;

import java.util.List;

public interface UserTravelCustomRepository {
    List<User> findUsersByTravelId(Long id);

    List<Travel> findTravelsByUserId(Long id);

    List<UserTravel> findAllByTravelId(Long travelId);

    void deleteAllByTravelId(Long travelId);

    List<String> findUserDeviceTokensByTravelId(Long travelId);
}
