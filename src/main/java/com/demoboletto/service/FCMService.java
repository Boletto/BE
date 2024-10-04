//package com.demoboletto.service;
//
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.Notification;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class FCMService {
//
//    private final FirebaseMessaging firebaseMessaging;
//
//    public void sendPushNotification(String deviceToken, String title, String body) {
//        try {
//            Message message = Message.builder()
//                    .setToken(deviceToken)
//                    .setNotification(Notification.builder()
//                            .setTitle(title)
//                            .setBody(body)
//                            .build())
//                    .putData("click_action", "FLUTTER_NOTIFICATION_CLICK") // 유니버설 링크 처리
//                    .putData("link", "https://boletto.site/friend-invitation") // Apple Universal Link
//                    .build();
//
//            firebaseMessaging.send(message);
//        } catch (FirebaseMessagingException e) {
//            throw new RuntimeException("FCM 알림 전송 실패: " + e.getMessage());
//        }
//    }
//}
