package com.demoboletto.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
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
        Message.Builder messageBuilder = Message.builder()
                .setToken(token);
        data.forEach(messageBuilder::putData);
        try {
            firebaseMessaging.send(messageBuilder.build());
        } catch (Exception e) {
            log.error("Failed to send message to user: {}", e.getMessage());
        }
    }

    @Async
    public void dispatchMessageToGroup(Map<String, String> data, List<String> tokens) {
        MulticastMessage messages = MulticastMessage.builder()
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
    public void pushMessageToGroup(String title, String body, List<String> tokens) {
        MulticastMessage messages = MulticastMessage.builder()
                .setNotification(createNotification(title, body))
                .addAllTokens(tokens)
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
