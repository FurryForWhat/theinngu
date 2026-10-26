package com.theinngu32.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {

    @Value("${aws.access.key.id}")
    private String accessKeyId;

    @Value("${aws.secret.access.key}")
    private String secretAccessKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.profile:NULL}")
    private String profile;

    @Bean
    public AwsBasicCredentials awsBasicCredentials() {
        return AwsBasicCredentials.create(accessKeyId, secretAccessKey);
    }

    @Bean
    public StaticCredentialsProvider staticCredentialsProvider(AwsBasicCredentials awsBasicCredentials) {
        return StaticCredentialsProvider.create(awsBasicCredentials);
    }

    @Bean
    public ProfileCredentialsProvider profileCredentialsProvider(AwsBasicCredentials awsBasicCredentials) {
        return ProfileCredentialsProvider.builder().profileName(profile).build();
    }

    @Bean
    public Region region() {
        return Region.of(region);
    }

    @Bean
    public S3Client s3Client(
            Region region,
            StaticCredentialsProvider staticCredentialsProvider,
            ProfileCredentialsProvider profileCredentialsProvider
    ) {
        return S3Client.builder()
                .region(region)
                .credentialsProvider("NULL".equals(profile) ? staticCredentialsProvider : profileCredentialsProvider)
                .build();
    }
}
