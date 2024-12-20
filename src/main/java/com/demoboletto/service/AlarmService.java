package com.demoboletto.service;

import com.demoboletto.components.NotificationComponent;
import com.demoboletto.domain.Travel;
import com.demoboletto.domain.User;
import com.demoboletto.domain.UserAlarm;
import com.demoboletto.dto.push.DispatchSystemEventDto;
import com.demoboletto.dto.push.DispatchTravelInviteDto;
import com.demoboletto.dto.push.NotiFriendAcceptDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.UserAlarmRepository;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.type.EAlarmType;
import com.demoboletto.type.ESilentEventType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmService {
    private final NotificationComponent notificationComponent;
    private final UserRepository userRepository;
    private final UserAlarmRepository userAlarmRepository;

    @Transactional
    public void travelInviteFriends(Travel travel, Long senderId, List<Long> members) {
        log.debug("tarvelInviteFriends: {}", travel);
        log.debug("members: {}", members);
        if (members.isEmpty()) {
            return;
        }

        List<String> deviceTokens = userRepository.findDeviceTokensByUserIds(members);

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        DispatchTravelInviteDto dispatchTravelEventDto = DispatchTravelInviteDto.builder()
                .eventType(EAlarmType.TRAVEL_TICKET)
                .value(sender.getNickname())
                .travelId(travel.getTravelId())
                .build();

        List<UserAlarm> userAlarms = members.stream()
                .map(member -> UserAlarm.builder()
                        .user(userRepository.findById(member)
                                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER))
                        )
                        .alarmType(EAlarmType.TRAVEL_TICKET)
                        .value(dispatchTravelEventDto.getValue())
                        .build()
                )
                .toList();

        userAlarmRepository.saveAll(userAlarms);


        notificationComponent.pushMessageToGroup(
                dispatchTravelEventDto.getTitle(),
                dispatchTravelEventDto.getMessage(),
                dispatchTravelEventDto.toMap(),
                deviceTokens
        );
    }

    public void sendFriendAcceptAlarm(User send, User to) {
        log.debug("sendFriendAcceptAlarm: sender: {}, to: {}", send, to);
        log.debug("to deviceToken: {}", to.getDeviceToken());
        log.debug("to userNickName: {}", to.getNickname());
        NotiFriendAcceptDto notiFriendAcceptDto = NotiFriendAcceptDto.builder()
                .value(send.getNickname())
                .eventType(EAlarmType.FRIEND_ACCEPT)
                .build();
        UserAlarm userAlarm = UserAlarm.builder()
                .user(to)
                .alarmType(EAlarmType.FRIEND_ACCEPT)
                .value(notiFriendAcceptDto.getValue())
                .build();
        userAlarmRepository.save(userAlarm);

        notificationComponent.pushMessageToUser(
                notiFriendAcceptDto.getTitle(),
                notiFriendAcceptDto.getMessage(),
                notiFriendAcceptDto.toMap(),
                to.getDeviceToken()
        );
    }

    public void dispatchSystemEvent(ESilentEventType eventType) {
        DispatchSystemEventDto dispatchSystemEventDto = DispatchSystemEventDto.builder()
                .eventType(eventType)
                .build();
        List<String> deviceTokens = userRepository.findDeviceTokensByAllUsers();
        notificationComponent.dispatchMessageToGroup(dispatchSystemEventDto.toMap(), deviceTokens);
    }
}
