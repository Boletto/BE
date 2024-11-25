package com.demoboletto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableJpaAuditing
public class BolettoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BolettoApplication.class, args);
    }

}
