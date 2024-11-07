package com.team25.backend.domain.admin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Service
public class S3Service {

    private final S3Presigner presigner;

    public S3Service(@Value("${s3.access-key}") String accessKey,
                     @Value("${s3.secret-access-key}") String secretAccessKey) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                accessKey, // Access Key ID
                secretAccessKey // Secret Access Key
        );
        this.presigner = S3Presigner.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }


    public URL generatePresignedUrl(String bucketName, String objectKey, Duration duration) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(duration)
                .getObjectRequest(getObjectRequest)
                .build();

        return presigner.presignGetObject(presignRequest).url();
    }
}