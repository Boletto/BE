package com.demoboletto.scheduler;

import com.demoboletto.components.NotificationComponent;
import com.demoboletto.domain.Travel;
import com.demoboletto.dto.push.DispatchTravelEventDto;
import com.demoboletto.dto.push.NotiTravelStartDto;
import com.demoboletto.repository.travel.TravelRepository;
import com.demoboletto.repository.travel.UserTravelRepository;
import com.demoboletto.type.EAlarmType;
import com.demoboletto.type.ESilentEventType;
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
    private final NotificationComponent notificationComponent;

    @Scheduled(cron = "0 0 0 * * *")
    public void startTravel() {
        List<Travel> travels = travelRepository.findTravelsByStartDate(LocalDate.now());
        travels.forEach(travel -> {
            List<String> deviceTokens = userTravelRepository.findUserDeviceTokensByTravelId(travel.getTravelId());
            DispatchTravelEventDto dispatchTravelEventDto = DispatchTravelEventDto.builder()
                    .eventType(ESilentEventType.TRAVEL_START)
                    .arriveArea(travel.getArrive())
                    .build();
            notificationComponent.dispatchMessageToGroup(dispatchTravelEventDto.toMap(), deviceTokens);
        });
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void startTravelNotification() {
        List<Travel> travels = travelRepository.findTravelsByStartDate(LocalDate.now());
        travels.forEach(travel -> {
            List<String> deviceTokens = userTravelRepository.findUserDeviceTokensByTravelId(travel.getTravelId());
            NotiTravelStartDto notiTravelStartDto = NotiTravelStartDto.builder()
                    .eventType(EAlarmType.TRAVEL_START)
                    .build();
            notificationComponent.pushMessageToGroup(
                    notiTravelStartDto.getTitle(),
                    notiTravelStartDto.getMessage(),
                    notiTravelStartDto.toMap(),
                    deviceTokens
            );
        });
    }

    @Scheduled(cron = "0 0 11 * * *")
    public void endTravel() {
        List<Travel> travels = travelRepository.findTravelsByEndDate(LocalDate.now());
        travels.forEach(travel -> {
            List<String> deviceTokens = userTravelRepository.findUserDeviceTokensByTravelId(travel.getTravelId());
            DispatchTravelEventDto dispatchTravelEventDto = DispatchTravelEventDto.builder()
                    .eventType(ESilentEventType.TRAVEL_END)
                    .arriveArea(travel.getArrive())
                    .build();
            notificationComponent.dispatchMessageToGroup(dispatchTravelEventDto.toMap(), deviceTokens);
        });
    }

    @Scheduled(cron = "0 0/5 * * * *")
    public void unlockTravel() {
        List<Travel> travels = travelRepository.findLockedTravels();
        travels.forEach(Travel::unlock);
        travelRepository.saveAll(travels);
    }

}
