package com.example.csvreaderapi.storage;

import java.util.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DynamoDbStorage {

    @Autowired
    private AmazonDynamoDB dynamoDbClient;

    @Autowired
    private DynamoDB dynamoDB;

    final String hashKey = "CsvRowId";
    final String rangeKey = "CsvRowNumber";
    private static final Logger logger = LoggerFactory.getLogger(DynamoDbStorage.class);

    public void createTable(String tableName) {
        Table table = dynamoDB.createTable(
                tableName,
                Arrays.asList(new KeySchemaElement(hashKey, KeyType.HASH),
                              new KeySchemaElement(rangeKey, KeyType.RANGE)),
                Arrays.asList(new AttributeDefinition(hashKey, ScalarAttributeType.S),
                              new AttributeDefinition(rangeKey, ScalarAttributeType.N)),
                new ProvisionedThroughput(1L, 1L));

        try {
            table.waitForActive();
        } catch (InterruptedException e) {
            throw new RuntimeException(tableName + " never became active.", e);
        }
    }

    public void save(String tableName, int counter, List<String> headers, List<String> values){
        UUID hashkeyValue = UUID.randomUUID();
        Map<String, AttributeValue> map = new HashMap<String, AttributeValue>();
        map.put(hashKey, new AttributeValue(hashkeyValue.toString()));
        map.put(rangeKey, new AttributeValue().withN(String.valueOf(counter)));

        for (int i=0; i<headers.size(); i++) {
            map.put(headers.get(i), new AttributeValue(values.get(i)));
        }

        PutItemRequest putItemRequest = new PutItemRequest(tableName, map);
        dynamoDbClient.putItem(putItemRequest);
        logger.info("Row saved with hashKey value of " + hashkeyValue + " and rangeKey value of " + counter);
    }
}



