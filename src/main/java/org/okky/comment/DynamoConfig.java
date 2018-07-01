package org.okky.comment;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories
class DynamoConfig {
    @Bean
    AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider credentialsProvider) {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .build();
    }
}