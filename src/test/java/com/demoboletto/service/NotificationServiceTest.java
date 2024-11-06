package com.demoboletto.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;

    @Test
    void pushMessageToUser() {
        notificationService.pushMessageToUser("Push Notification Test Sender: Server", "is Normal Notification", "elKHdfGTvUhHjKxhsn3c8f:APA91bGv3cykXdp_Rl9z_3L09bckC3VVete3pACgZeveRIOT1AJfSvK-cZrEdKIJD0f-5KOnzs4r-d2o2SIjyZv6NwP5A751BOHERA2zd2Sm7G2GSlqlUe8");
    }

    @Test
    void dispatchMessageToUser() {
    }
}