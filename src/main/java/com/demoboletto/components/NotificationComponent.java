package com.demoboletto.components;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationComponent {
    private final FirebaseMessaging firebaseMessaging;

    //send for ios apns
    @Async
    public void pushMessageToUser(String title, String body, String token) {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(createNotification(title, body))
                .build();
        try {
            firebaseMessaging.send(message);
        } catch (Exception e) {
            log.error("Failed to send message to user: {}", e.getMessage());
        }
    }

    @Async
    public void dispatchMessageToUser(Map<String, String> data, String token) {
        Message message = Message.builder()
                .setToken(token)
                .putAllData(data) // 사용자 정의 데이터 추가
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setContentAvailable(true) // 중요: iOS에서 Silent Push 트리거
                                .build())
                        .build())
                .build();

        try {
            firebaseMessaging.send(message);
            log.info("Message sent successfully to token: {}", token);
        } catch (Exception e) {
            log.error("Failed to send message to user: {}", e.getMessage());
        }
    }

    @Async
    public void dispatchMessageToGroup(Map<String, String> data, List<String> tokens) {
        MulticastMessage messages = MulticastMessage.builder()
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setContentAvailable(true) // 중요: iOS에서 Silent Push 트리거
                                .build())
                        .build())
                .putAllData(data)
                .addAllTokens(tokens)
                .build();
        try {
            firebaseMessaging.sendEachForMulticast(messages);
        } catch (Exception e) {
            log.error("Failed to send message to user: {}", e.getMessage());
        }
    }

    @Async
    public void pushMessageToGroup(String title, String body, Map<String, String> data, List<String> tokens) {
        MulticastMessage messages = MulticastMessage.builder()
                .setNotification(createNotification(title, body))
                .addAllTokens(tokens)
                .putAllData(data)
                .build();
        try {
            firebaseMessaging.sendEachForMulticast(messages);
        } catch (Exception e) {
            log.error("Failed to send message to user: {}", e.getMessage());
        }
    }

    private Notification createNotification(String title, String body) {
        return Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
    }
}
