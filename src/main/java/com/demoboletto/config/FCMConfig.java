package com.demoboletto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
public class FCMConfig {
    private final ClassPathResource firebaseResource = new ClassPathResource(
            "boletto-5a414-firebase-adminsdk-ivaiw-4838d9285c.json");

    @Bean
    FirebaseApp firebaseApp() throws IOException {
        if (!firebaseResource.exists()) {
            throw new FileNotFoundException("Firebase service account JSON file not found!");
        }
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(
                        firebaseResource.getInputStream()))
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        return FirebaseMessaging.getInstance(firebaseApp());
    }
}