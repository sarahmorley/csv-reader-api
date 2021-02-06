package com.example.csvreaderapi.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.example.csvreaderapi.service.CsvReaderService;
import com.example.csvreaderapi.storage.AwsTableUtils;
import com.example.csvreaderapi.storage.DynamoDbStorage;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CsvReaderService.class})
public class TestConfigCsvReader {

    @Bean
    public DynamoDbStorage dynamoDbStorage (){
        return Mockito.mock(DynamoDbStorage.class);
    }

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

    @Bean
    public AwsTableUtils awsTableUtils() { return Mockito.mock(AwsTableUtils.class);}


}
