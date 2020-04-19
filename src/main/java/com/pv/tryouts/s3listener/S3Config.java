package com.pv.tryouts.s3listener;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config extends AwsConfig {

    @Bean
    public AmazonS3 s3Client(){
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
            new BasicAWSCredentials(awsAccessKey, awsSecretKey));
        AmazonS3 client = AmazonS3ClientBuilder.standard()
        .withRegion(awsRegion)
        .withCredentials(awsCredentialsProvider).build();
        return client;   
    }
}