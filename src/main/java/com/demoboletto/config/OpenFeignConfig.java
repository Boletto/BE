package com.demoboletto.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.demoboletto.client")
public class OpenFeignConfig {
}
