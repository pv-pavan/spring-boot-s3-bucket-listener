# spring-boot-s3-bucket-listener
Sample project for your java application to get notified for files updated on an AWS S3 bucket

-- 
### How does it work?

S3 bucket is configured to send object create events to a queue on SQS
This sprig boot server will listen for the same topic and process those events

--

### Steps:
1. Create a s3 bucket eg: 's3-listener-test-bucket'
2. Create a SQS queue eg: 's3-sqs-sboot-topic'
3. Add permission to the SQS topic to receive notifications from this s3 bucket only

```
{
  "Version": "2012-10-17",
  "Id": "arn:aws:sqs:ap-south-1:<account-id>:s3-sqs-sboot-topic/SQSDefaultPolicy",
  "Statement": [
    {
      "Sid": "Sid15872900",
      "Effect": "Allow",
      "Principal": "*",
      "Action": "SQS:*",
      "Resource": "arn:aws:sqs:ap-south-1:<account-id>:s3-sqs-sboot-topic",
      "Condition": {
        "ArnEquals": {
          "aws:SourceArn": "arn:aws:s3:::s3-listener-test-bucket"
        }
      }
    }
  ]
}
```

4. Add event notification on the S3 bucket to the sqs topic 's3-sqs-sboot-topic' for all object created events
5. Test if uploading a file triggers an event to the topic.



## sources
- https://docs.aws.amazon.com/AmazonS3/latest/dev/ways-to-add-notification-config-to-bucket.html#step1-create-sqs-queue-for-notification
- https://docs.aws.amazon.com/AmazonS3/latest/dev/notification-content-structure.html