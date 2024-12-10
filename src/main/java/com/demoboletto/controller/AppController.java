package com.demoboletto.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/invite/**")
    public ResponseEntity<Resource> getMetadata() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/html");

        Resource resource = new ClassPathResource("static/metadata.html");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
