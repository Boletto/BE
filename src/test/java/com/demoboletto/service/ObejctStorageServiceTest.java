package com.demoboletto.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("prod")
class ObjectStorageServiceTest {

    @Autowired
    private ObjectStorageService objectStorageService;

    @Test
    void uploadFile() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "This is a test file".getBytes()
        );
        // Add any file to test
        String result = objectStorageService.uploadFile(mockFile, 1234L);
        System.out.println("result = " + result);
        assertNotNull(result);
    }
}