package com.demoboletto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
public class AWSS3Service {
    @Autowired
    private S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path tempFile = Files.createTempFile("", fileName);
        Files.write(tempFile, file.getBytes());

        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .contentType(file.getContentType())
                        .acl("public-read")
                        .build(),
                tempFile);
        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }
}
