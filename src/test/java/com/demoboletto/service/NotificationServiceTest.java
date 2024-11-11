package com.demoboletto.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationServiceTest {
    private final String token = "dEBtBVqa6kfAqyMs-IpFIj:APA91bH9Aan2_udupW1GxHg3EU3hM7LPiPJhBiMgCqiJXHrAx2Vgri7cx5sUKm8DJQwa5k1WSEz1S3mi-hiIxGY53wVxovonsMF-7ZdzPvCUGDRFxotVfco";
    @Autowired
    private NotificationService notificationService;

    @Test
    void pushMessageToUser() {
        notificationService.pushMessageToUser("Push Notification Test Sender: Server", "is Normal Notification", token);
    }

    @Test
    void dispatchMessageToUser() {
        Map<String, String> map = Map.of("operationType", "syncData", "extraInfo", "Silent Push Test");
        notificationService.dispatchMessageToUser(map, token);
    }
}