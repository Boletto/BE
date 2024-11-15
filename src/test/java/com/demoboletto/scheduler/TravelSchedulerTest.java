package com.demoboletto.scheduler;

import com.demoboletto.domain.Travel;
import com.demoboletto.dto.push.DispatchTravelEventDto;
import com.demoboletto.repository.travel.TravelRepository;
import com.demoboletto.repository.travel.UserTravelRepository;
import com.demoboletto.service.NotificationService;
import com.demoboletto.type.ETravelEventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : hyunwoopark
 * @version : 1.0.0
 * @package : demo-boletto
 * @name : TravelSchedulerTest
 * @date : 2024. 11. 14. 오후 5:54
 * @modifyed : $
 **/
@SpringBootTest
@ActiveProfiles("local")
class TravelSchedulerTest {
    @Autowired
    TravelRepository travelRepository;
    @Autowired
    UserTravelRepository userTravelRepository;
    @Autowired
    NotificationService notificationService;

    @Test
    void startTravel() {
        List<Travel> travels = travelRepository.findTravelsByStartDate(LocalDate.now());
        travels.forEach(travel -> {
            List<String> deviceTokens = userTravelRepository.findUserDeviceTokensByTravelId(travel.getTravelId());
            System.out.println("deviceTokens = " + deviceTokens);
            DispatchTravelEventDto dispatchTravelEventDto = DispatchTravelEventDto.builder()
                    .eventType(ETravelEventType.TRAVEL_START)
                    .arriveArea(travel.getArrive())
                    .build();
            notificationService.dispatchMessageToGroup(dispatchTravelEventDto.toMap(), deviceTokens);
        });
    }

    @Test
    void endTravel() {
//        travelScheduler.endTravel();
    }
}