package com.example.csvreaderapi.storage;

import java.util.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
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
    private AwsTableUtils awsTableUtils;

    final String hashKey = "CsvRowId";
    final String rangeKey = "CsvRowNumber";
    private static final Logger logger = LoggerFactory.getLogger(DynamoDbStorage.class);

    public void createTable(String tableName) {
        List<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName(hashKey).withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName(rangeKey).withAttributeType("N"));

        List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement().withAttributeName(hashKey).withKeyType(KeyType.HASH));
        keySchema.add(new KeySchemaElement().withAttributeName(rangeKey).withKeyType(KeyType.RANGE));

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput()
                .withReadCapacityUnits(1L)
                .withWriteCapacityUnits(1L));

        dynamoDbClient.createTable(request);
        awsTableUtils.CheckTableIsActive(request);
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



