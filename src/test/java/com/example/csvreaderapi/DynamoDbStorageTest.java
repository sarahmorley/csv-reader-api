package com.example.csvreaderapi;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.example.csvreaderapi.configuration.TestConfig;
import com.example.csvreaderapi.storage.DynamoDbStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

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


    }
}
