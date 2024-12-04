package com.demoboletto.service;

import com.demoboletto.components.NotificationComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class NotificationComponentTest {
    private final String token = "dEBtBVqa6kfAqyMs-IpFIj:APA91bH9Aan2_udupW1GxHg3EU3hM7LPiPJhBiMgCqiJXHrAx2Vgri7cx5sUKm8DJQwa5k1WSEz1S3mi-hiIxGY53wVxovonsMF-7ZdzPvCUGDRFxotVfco";
    @Autowired
    private NotificationComponent notificationComponent;

    @Test
    void pushMessageToUser() {
        notificationComponent.pushMessageToUser("Push Notification Test Sender: Server", "is Normal Notification", token);
    }

    @Test
    void dispatchMessageToUser() {
        Map<String, String> map = Map.of("operationType", "syncData", "extraInfo", "Silent Push Test");
        notificationComponent.dispatchMessageToUser(map, token);
    }

    @Test
    void dispatchMessageToGroup() {
    }

    @Test
    void pushMessageToGroup() {
    }
}