package com.example.csvreaderapi.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.example.csvreaderapi.service.CsvReader;
import com.example.csvreaderapi.storage.DynamoDbStorage;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({CsvReader.class, DynamoDbStorage.class})
public class TestConfig {


    @Bean
    public AWSStaticCredentialsProvider amazonAWSCredentials() {
        return Mockito.mock(AWSStaticCredentialsProvider.class);
    }

    @Bean
    public AmazonDynamoDB dynamoDbClient(AWSStaticCredentialsProvider amazonAWSCredentials){
        return Mockito.mock(AmazonDynamoDB.class);
    }

    @Bean
    public DynamoDB dynamoDB(AmazonDynamoDB client){
        return Mockito.mock(DynamoDB.class);
    }

}
