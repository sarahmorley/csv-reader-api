package com.example.csvreaderapi.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.example.csvreaderapi.configuration.TestConfigDynamo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfigDynamo.class)
public class TestDynamoDbStorage {

    @Autowired
    private DynamoDbStorage dynamoDbStorage;

    @Autowired
    private AmazonDynamoDB dynamoDbClient;

    final String tableName = "CsvTestTable";
    final String hashKey = "CsvRowId";
    final String rangeKey = "CsvRowNumber";

    @Test
    public void testSave() {
        List<String> headers = Arrays.asList("name", "address");
        List<String> values = Arrays.asList("Sarah", "Galway");

        dynamoDbStorage.save(tableName, 3, headers, values);

        ArgumentCaptor <PutItemRequest> argumentCaptor = ArgumentCaptor.forClass(PutItemRequest.class);
        Mockito.verify(dynamoDbClient).putItem(argumentCaptor.capture());
        PutItemRequest capturedArgument = argumentCaptor.getValue();

        String actualTableName = capturedArgument.getTableName();
        assertEquals(tableName, actualTableName);

        Map<String, AttributeValue> actualMap = capturedArgument.getItem();
        String actualAddress = actualMap.get("address").getS();
        String actualName = actualMap.get("name").getS();
        String actualCsvRowNumber =  actualMap.get("CsvRowNumber").getN();
        String actualUUID = actualMap.get("CsvRowId").getS();

        assertEquals("Galway", actualAddress);
        assertEquals("Sarah", actualName);
        assertEquals("3", actualCsvRowNumber);
        assertNotNull(actualUUID);
    }

    @Test
    public void testCreateTable() {
        dynamoDbStorage.createTable(tableName);
        ArgumentCaptor <CreateTableRequest> argumentCaptor = ArgumentCaptor.forClass(CreateTableRequest.class);
        Mockito.verify(dynamoDbClient).createTable(argumentCaptor.capture());
        CreateTableRequest capturedArgument = argumentCaptor.getValue();

        String actualTableName = capturedArgument.getTableName();
        assertEquals(tableName, actualTableName);

        List<KeySchemaElement> actualKeysCreated = capturedArgument.getKeySchema();
        String actualHashKeyName = actualKeysCreated.get(0).getAttributeName();
        String actualRangeKeyName = actualKeysCreated.get(1).getAttributeName();

        assertEquals(hashKey, actualHashKeyName);
        assertEquals(rangeKey, actualRangeKeyName);
    }
}
