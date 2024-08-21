package com.dynamoDbTest.demoDynamoDbTest.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDbConfig {


    @Value("${aws.dynamodb.endpoint}")
    private String dynamodbLocation;
    @Value("${aws.dynamodb.accessKey}")
    private String accessKey;
    @Value("${aws.dynamodb.secretKey}")
    private String secretKey;


    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(amazonDynamoDb());
    }

    private AmazonDynamoDB amazonDynamoDb() {
        return AmazonDynamoDBAsyncClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamodbLocation, "ap-southeast-2")).withCredentials(awsCredentialsProvider()).build();
    }

    private AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }


}
