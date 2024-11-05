package com.demoboletto.config;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.transfer.UploadConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class OracleCloudConfig {
    @Value("${spring.cloud.oci.credentials.fingerprint}")
    private String fingerprint;
    @Value("${spring.cloud.oci.credentials.user}")
    private String user;
    @Value("${spring.cloud.oci.credentials.tenancy}")
    private String tenancy;
    @Value("${spring.cloud.oci.credentials.region}")
    private String region;
    @Value("${spring.cloud.oci.credentials.key-file}")
    private String keyFile;

    @Bean
    public ObjectStorageClient objectStorageClient() {
        ClassPathResource resource = new ClassPathResource(keyFile);
        SimpleAuthenticationDetailsProvider provider = SimpleAuthenticationDetailsProvider.builder()
                .fingerprint(fingerprint)
                .userId(user)
                .tenantId(tenancy)
                .region(Region.fromRegionCodeOrId(region))
                .privateKeySupplier(() -> {
                    try {
                        return resource.getInputStream();
                    } catch (java.io.FileNotFoundException e) {
                        throw new RuntimeException("Key file not found", e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .build();

        return ObjectStorageClient.builder().region(Region.AP_CHUNCHEON_1).build(provider);
    }

    @Bean
    public UploadConfiguration uploadConfiguration() {
        return UploadConfiguration.builder()
                .allowMultipartUploads(true)
                .allowParallelUploads(true)
                .lengthPerUploadPart(16)
                .build();
    }
}
