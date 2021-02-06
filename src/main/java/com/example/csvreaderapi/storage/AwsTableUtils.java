package com.example.csvreaderapi.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AwsTableUtils {

    @Autowired
    private AmazonDynamoDB dynamoDbClient;

    private static final Logger logger = LoggerFactory.getLogger(AwsTableUtils.class);

    public void CheckTableIsActive(CreateTableRequest request){
        try{
            TableUtils.waitUntilActive(dynamoDbClient, request.getTableName());
            logger.info(request.getTableName() + " is active");
        }
        catch (Exception e){
            throw new RuntimeException(request.getTableName() + " never became active.", e);
        }
    }
}
