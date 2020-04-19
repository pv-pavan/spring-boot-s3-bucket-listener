package com.pv.tryouts.s3listener;

import java.util.List;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class S3ListenerApplication {

    private static final String QUEUE_NAME = "s3-sqs-sboot-topic";

    @Autowired
    QueueMessagingTemplate queueMessagingTemplate;

    public static void main(String[] args) {
        log.debug("Running app");
        SpringApplication.run(S3ListenerApplication.class, args);
    }

    @SqsListener(QUEUE_NAME)
    public void receiveMessage(String message) {
        log.debug("Received message: " + message);
        log.debug("S3 URL: " + getS3UrlFromSQSEvent(message));
    }

    private String getS3UrlFromSQSEvent(String event) {
        ObjectMapper objectMapper = new ObjectMapper();
        S3EventNotification s3Event = null;
        try {
            s3Event = objectMapper.readValue(event, S3EventNotification.class);
        } catch (JsonMappingException e) {
            log.error(e.getMessage(), e);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        List<S3EventNotificationRecord> records = s3Event.getRecords();
        //Assume event has only one record
        if(records.size()<=0) return "";
        S3EventNotificationRecord r = records.get(0);
        String region = r.getAwsRegion(); //Assume region is not default, the S3 URL is different for us-east-1
        String bucketName = r.getS3().getBucket().getName();
        String fileName = r.getS3().getObject().getKey();
        StringBuilder builder = new StringBuilder();
        builder.append("https://")
        .append(bucketName)
        .append(".s3.")
        .append(region)
        .append(".amazonaws.com/")
        .append(fileName);

        return builder.toString();
    }



}
