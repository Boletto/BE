package com.demoboletto.service;

import com.demoboletto.domain.Alarm;
import com.demoboletto.domain.Travel;
import com.demoboletto.domain.UserAlarm;
import com.demoboletto.domain.User;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.AlarmRepository;
import com.demoboletto.repository.UserAlarmRepository;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.type.EAlarmType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserAlarmRepository userAlarmRepository;
    private final UserRepository userRepository;
    private final FCMService fcmService; // FCM 알람 전송 서비스

    @Transactional
    public void sendFriendInviteAlarm(Long userId, Long friendId, Travel travel) {
        // 알림 템플릿 생성
        Alarm alarmTemplate = Alarm.builder()
                .alarmType(EAlarmType.TRAVEL_TICKET)
                .message("여행 티켓이 도착했어요!") // 템플릿 메시지
                .build();

        alarmRepository.save(alarmTemplate);

        // 초대받은 유저(UserAlarm)에 알림 저장
        UserAlarm userAlarm = UserAlarm.builder()
                .user(getUser(friendId)) // 친구(userId)에게 알람 발송
                .alarmTemplate(alarmTemplate)
                .build();

        userAlarmRepository.save(userAlarm);

        // FCM 푸시 알람 전송
        String messageBody = createTravelInviteMessage(getUser(userId), travel);
        fcmService.sendPushNotification(getUser(friendId).getDeviceToken(), "친구 초대", messageBody);
    }

    // 메시지 본문을 템플릿으로 생성
    private String createTravelInviteMessage(User inviter, Travel travel) {
        return inviter.getNickname() + "님이 여행 티켓을 초대했습니다.\n"
                + "출발지: " + travel.getDeparture() + "\n"
                + "도착지: " + travel.getArrive() + "\n"
                + "여행 기간: " + travel.getStartDate() + " ~ " + travel.getEndDate();
    }

    // 유저를 찾는 헬퍼 메서드
    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
    }
}
