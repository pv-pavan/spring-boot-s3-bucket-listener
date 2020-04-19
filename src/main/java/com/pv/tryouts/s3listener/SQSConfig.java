package com.pv.tryouts.s3listener;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SQSConfig extends AwsConfig {
    
    @Bean
    public AmazonSQSAsync amazonSQS() {
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
            new BasicAWSCredentials(awsAccessKey, awsSecretKey));
        AmazonSQSAsync amazonSQS = AmazonSQSAsyncClientBuilder.standard()
        .withRegion(awsRegion)
        .withCredentials(awsCredentialsProvider).build();
        return amazonSQS;   
    }


    @Bean
	public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
		return new QueueMessagingTemplate(amazonSQSAsync);
	}
}