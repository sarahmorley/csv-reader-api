package com.example.csvreaderapi.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
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

    @Autowired
    private DynamoDB dynamoDB;

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
        Mockito.when(dynamoDB.createTable(Mockito.eq(tableName), Mockito.anyList(), Mockito.anyList(), Mockito.any()))
               .thenReturn(Mockito.mock(Table.class));

        dynamoDbStorage.createTable(tableName);

        ArgumentCaptor <String> argumentCaptorTableName = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor <List<KeySchemaElement>> argumentCaptorKeys = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor <List<AttributeDefinition>> argumentCaptorAttributes = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor <ProvisionedThroughput> argumentCaptorThroughput = ArgumentCaptor.forClass(ProvisionedThroughput.class);

        Mockito.verify(dynamoDB).createTable(argumentCaptorTableName.capture(), argumentCaptorKeys.capture(),
                                             argumentCaptorAttributes.capture(), argumentCaptorThroughput.capture());

        String actualTableName = argumentCaptorTableName.getValue();
        assertEquals(tableName, actualTableName);

        List<KeySchemaElement> actualKeysCreated = argumentCaptorKeys.getValue();
        String actualHashKeyName = actualKeysCreated.get(0).getAttributeName();
        String actualRangeKeyName = actualKeysCreated.get(1).getAttributeName();

        assertEquals(hashKey, actualHashKeyName);
        assertEquals(rangeKey, actualRangeKeyName);
    }
}
