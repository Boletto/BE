package com.demoboletto.service;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.transfer.UploadConfiguration;
import com.oracle.bmc.objectstorage.transfer.UploadManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ObjectStorageService {

    private final ObjectStorageClient client;
    private final UploadConfiguration uploadConfiguration;

    @Value("${spring.cloud.oci.object-storage.bucket}")
    private String bucketName;
    @Value("${spring.cloud.oci.object-storage.namespace}")
    private String namespace;
    @Value("${spring.cloud.oci.credentials.region}")
    private String region;

    public String uploadFile(MultipartFile file, Long userId) throws IOException {
//        Initiate File Object and Create File Metadata
        File body = convertMultipartFileToFile(file);
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String fileExtension = getFileExtension(file.getOriginalFilename());
        // Create a unique file name Ex. 1234/20210901/UUID.jpg
        String fileName = String.format("%d/%s/%s.%s", userId, currentDate, UUID.randomUUID(), fileExtension);

        UploadManager uploadManager = new UploadManager(client, uploadConfiguration);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucketName(bucketName)
                .namespaceName(namespace)
                .objectName(fileName)
                .contentType(file.getContentType())
                .build();

        UploadManager.UploadRequest uploadRequest = UploadManager
                .UploadRequest.builder(body)
                .allowOverwrite(true)
                .build(putObjectRequest);
        UploadManager.UploadResponse uploadResponse = uploadManager.upload(uploadRequest);

        return String.format("https://objectstorage.%s.oraclecloud.com/n/%s/b/%s/o/%s",
                region, namespace, bucketName, fileName);
    }

    public void deleteFile(String fileName) {
        client.deleteObject(DeleteObjectRequest.builder()
                .bucketName(bucketName)
                .namespaceName(namespace)
                .objectName(fileName)
                .build());
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(convFile);
        return convFile;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

}
