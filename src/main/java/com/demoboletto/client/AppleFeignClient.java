package com.demoboletto.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "AppleFeignClient", url = "${apple.api_url.information}")
public interface AppleFeignClient {
    @GetMapping
    String call();
}
