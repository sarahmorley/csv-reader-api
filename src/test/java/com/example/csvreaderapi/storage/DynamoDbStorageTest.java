package com.example.csvreaderapi.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.example.csvreaderapi.configuration.TestConfig;
import com.example.csvreaderapi.storage.DynamoDbStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class DynamoDbStorageTest {

    @Autowired
    private DynamoDbStorage dynamoDbStorage;

    @Autowired
    private AmazonDynamoDB dynamoDbClient;

    @Test
    public void testSave() {
        List<String> headers = new ArrayList<>();
        headers.add("name");
        headers.add("address");
        List<String> values = new ArrayList<>();
        values.add("Sarah");
        values.add("Galway");

        dynamoDbStorage.save("testTable", 3, headers, values);

        UUID hashkeyValue = UUID.randomUUID();
        Map<String, AttributeValue> map = new HashMap<String, AttributeValue>();
        map.put("hashKey", new AttributeValue(hashkeyValue.toString()));
        map.put("rangeKey", new AttributeValue().withN(String.valueOf(3)));
        for (int i=0; i<headers.size(); i++) {
            map.put(headers.get(i), new AttributeValue(values.get(i)));
        }

        ArgumentCaptor <PutItemRequest> argumentCaptor = ArgumentCaptor.forClass(PutItemRequest.class);
        Mockito.verify(dynamoDbClient).putItem(argumentCaptor.capture());
        PutItemRequest capturedArgument = argumentCaptor.getValue();

        String actualTableName = capturedArgument.getTableName();
        assertEquals("testTable", actualTableName);

        Map<String, AttributeValue> actualMap = capturedArgument.getItem();
        String actualAddress = actualMap.get("address").toString();
        String actualName = actualMap.get("name").toString();
        String actualCsvRowNumber =  actualMap.get("CsvRowNumber").toString();

        assertEquals("{S: Galway,}", actualAddress);
        assertEquals("{S: Sarah,}", actualName);
        assertEquals("{N: 3,}", actualCsvRowNumber);
    }
}
