package com.example.csvreaderapi.service;

import com.example.csvreaderapi.storage.DynamoDbStorage;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class CsvReaderService {

    @Autowired
    private DynamoDbStorage dynamoDbStorage;

    @Value("${file.path}")
    private String csvFilePath;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public List<List<String>> loadCsv(String parity) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(csvFilePath);
        List<List<String>> csvList = new ArrayList<List<String>>();
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, "UTF-8"));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String tableName = "CsvRows_" + sdf.format(timestamp);
        String[] headers = csvReader.readNext();
        if (headers.length == 0)
            return csvList;
        
        dynamoDbStorage.createTable(tableName);
        String[] values = null;
        int counter = 0;
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
            else if (parity.equals("")){
                csvList.add(Arrays.asList(values));
                dynamoDbStorage.save(tableName, counter, Arrays.asList(headers), Arrays.asList(values));
            }

        }
        return csvList;
    }

}
