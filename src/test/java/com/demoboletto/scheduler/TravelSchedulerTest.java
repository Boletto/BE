package com.demoboletto.scheduler;

import com.demoboletto.components.NotificationComponent;
import com.demoboletto.domain.Travel;
import com.demoboletto.dto.push.DispatchTravelEventDto;
import com.demoboletto.repository.travel.TravelRepository;
import com.demoboletto.repository.travel.UserTravelRepository;
import com.demoboletto.type.ESilentEventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

/**
 * @author : hyunwoopark
 * @version : 1.0.0
 * @package : demo-boletto
 * @name : TravelSchedulerTest
 * @date : 2024. 11. 14. 오후 5:54
 * @modifyed : $
 **/
@SpringBootTest
@ActiveProfiles("prod")
class TravelSchedulerTest {
    @Autowired
    TravelRepository travelRepository;
    @Autowired
    UserTravelRepository userTravelRepository;
    @Autowired
    NotificationComponent notificationComponent;

    @Test
    void startTravel() {
        List<Travel> travels = travelRepository.findTravelsByStartDate(LocalDate.now());
        travels.forEach(travel -> {
            List<String> deviceTokens = userTravelRepository.findUserDeviceTokensByTravelId(travel.getTravelId());
            System.out.println("deviceTokens = " + deviceTokens);
            DispatchTravelEventDto dispatchTravelEventDto = DispatchTravelEventDto.builder()
                    .eventType(ESilentEventType.TRAVEL_START)
                    .arriveArea(travel.getArrive())
                    .build();
            notificationComponent.dispatchMessageToGroup(dispatchTravelEventDto.toMap(), deviceTokens);
        });
    }

    @Test
    void endTravel() {
//        travelScheduler.endTravel();
    }

    @Test
    void unlockTravel() {
        List<Travel> travels = travelRepository.findLockedTravels();
        travels.forEach(Travel::unlock);
        travelRepository.saveAll(travels);
    }
}