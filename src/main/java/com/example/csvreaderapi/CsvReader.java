package com.example.csvreaderapi;

import com.example.csvreaderapi.storage.DynamoDbStorage;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CsvReader {
    @Autowired
    private DynamoDbStorage dynamoDbStorage;

    public List<List<String>> loadCsv(String parity) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/PracticeFile.csv");
        List<List<String>> csvList = new ArrayList<List<String>>();
        String tableName = "SarahTable3";
        dynamoDbStorage.createTable(tableName);
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, "UTF-8"));
        String[] values = null;
        int counter = 0;
        String[] headers = csvReader.readNext();
        while((values = csvReader.readNext()) != null){
            counter++;
            if(counter % 2 == 0 && parity.equals("EVEN")){
                csvList.add(Arrays.asList(values));
                dynamoDbStorage.save(tableName, counter, Arrays.asList(headers), Arrays.asList(values));
            }
            else if(counter % 2 != 0 && parity.equals("ODD")){
                csvList.add(Arrays.asList(values));
                dynamoDbStorage.save(tableName, counter, Arrays.asList(headers), Arrays.asList(values));
            }
            else if (parity == null || parity.length() == 0){
                csvList.add(Arrays.asList(values));
                dynamoDbStorage.save(tableName, counter, Arrays.asList(headers), Arrays.asList(values));
            }
        }
        return csvList;
    }

    public List<String> getHeaders(List<List<String>> csvList) {
        List<String> headers = csvList.get(0);
        return headers;
    }

}
