package com.demoboletto.scheduler;

import com.demoboletto.domain.Travel;
import com.demoboletto.dto.push.DispatchTravelEventDto;
import com.demoboletto.repository.travel.TravelRepository;
import com.demoboletto.repository.travel.UserTravelRepository;
import com.demoboletto.service.NotificationService;
import com.demoboletto.type.ETravelEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TravelScheduler {
    private final TravelRepository travelRepository;
    private final UserTravelRepository userTravelRepository;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 0 * * *")
    public void startTravel() {
        List<Travel> travels = travelRepository.findTravelsByStartDate(LocalDate.now());
        travels.forEach(travel -> {
            List<String> deviceTokens = userTravelRepository.findUserDeviceTokensByTravelId(travel.getTravelId());
            DispatchTravelEventDto dispatchTravelEventDto = DispatchTravelEventDto.builder()
                    .eventType(ETravelEventType.TRAVEL_START)
                    .arriveArea(travel.getArrive())
                    .build();
            notificationService.dispatchMessageToGroup(dispatchTravelEventDto.toMap(), deviceTokens);
        });
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void endTravel() {
        List<Travel> travels = travelRepository.findTravelsByEndDate(LocalDate.now());
        travels.forEach(travel -> {
            List<String> deviceTokens = userTravelRepository.findUserDeviceTokensByTravelId(travel.getTravelId());
            DispatchTravelEventDto dispatchTravelEventDto = DispatchTravelEventDto.builder()
                    .eventType(ETravelEventType.TRAVEL_END)
                    .arriveArea(travel.getArrive())
                    .build();
            notificationService.dispatchMessageToGroup(dispatchTravelEventDto.toMap(), deviceTokens);
        });
    }

}
