package com.pv.tryouts.s3listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${cloud.aws.credentials.accessKey}")
    protected String awsAccessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    protected String awsSecretKey;

    @Value("${cloud.aws.region.static}")
    protected String awsRegion;

}