package com.example.csvreaderapi.configuration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    //These are only here as I am using DynamoDB local
    private String amazonAWSAccessKey = "test_access_key";
    private String amazonAWSSecretKey = "test_secret_key";
    private String awsDynamoDBUrl = "http://localhost:8000";

    @Bean
    public AWSStaticCredentialsProvider amazonAWSCredentials() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
    }

    @Bean
    public AmazonDynamoDB dynamoDbClient(AWSStaticCredentialsProvider amazonAWSCredentials){
        AmazonDynamoDB dynamo = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsDynamoDBUrl, Regions.US_EAST_1.name()))
                .withCredentials(amazonAWSCredentials)
                .build();
        return dynamo;
    }

    @Bean
    public DynamoDB dynamoDB(AmazonDynamoDB client){
        DynamoDB dynamo = new DynamoDB(client);
        return dynamo;
    }

}
