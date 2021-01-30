package com.example.csvreaderapi.service;

import com.example.csvreaderapi.storage.DynamoDbStorage;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CsvReader {

    @Autowired
    private DynamoDbStorage dynamoDbStorage;

    String csvFilePath = "/PracticeFile.csv";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public List<List<String>> loadCsv(String parity) throws IOException {
        String[] values = null;
        int counter = 0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String tableName = csvFilePath.substring(1) + "_" + sdf.format(timestamp);

        InputStream inputStream = getClass().getResourceAsStream(csvFilePath);
        List<List<String>> csvList = new ArrayList<List<String>>();
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, "UTF-8"));

        String[] headers = csvReader.readNext();
        if (headers.length > 0)
            dynamoDbStorage.createTable(tableName);

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
    
}