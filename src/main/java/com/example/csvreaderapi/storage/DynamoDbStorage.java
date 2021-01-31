package com.example.csvreaderapi.storage;

import java.util.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DynamoDbStorage {

    @Autowired
    private AmazonDynamoDB dynamoDbClient;

    @Autowired
    private DynamoDB dynamo;

    final String hashKey = "CsvRowId";
    final String rangeKey = "CsvRowNumber";

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

        try{
            TableUtils.waitUntilActive(dynamoDbClient, request.getTableName());
        }
        catch (Exception e){
            System.err.println(e.getMessage());
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

        try {
            dynamoDbClient.putItem(putItemRequest);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}



