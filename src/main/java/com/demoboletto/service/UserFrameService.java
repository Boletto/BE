package com.demoboletto.service;

import com.demoboletto.repository.UserFrameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFrameService {
    private final UserFrameRepository userFrameRepository;
}
