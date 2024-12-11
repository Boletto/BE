package com.demoboletto.service;

import com.demoboletto.components.NotificationComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class NotificationComponentTest {
    private final String token = "eAwci3ZL9UMIo_suBpM39t:APA91bHgCA-luXtol0OkCSUm8WX5cCQ-ssgiQjzOj9wcLBHPHAucf1cRVy35T6N9b7eb5KTzoue-hl8d4GgzOD5EjWmx6e_XOSJRD7qtlvdJvv1-XjDJyVU";
    @Autowired
    private NotificationComponent notificationComponent;

    @Test
    void pushMessageToUser() {
        notificationComponent.pushMessageToUser("Push Notification Test Sender: Server", "is Normal Notification", null, token);
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