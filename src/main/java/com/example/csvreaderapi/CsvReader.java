package com.example.csvreaderapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader {

    public List<List<String>> loadCsv() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/PracticeFile.csv");
//TODO - use openCSV library
        BufferedReader csvReader = null;
        List<List<String>> csvList = new ArrayList<List<String>>();
        String csvRecord = null;
        int counter = 0;
        csvReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String headers = csvReader.readLine();
        //Create the table dynamoDB
        while ((csvRecord = csvReader.readLine()) != null) {
            counter ++;
            //Odd even logic
            //Insert into DB - pass counter, headers and list
            //insert method will need to map the headers to the list in dictionary

            String[] values = csvRecord.split(",");
            csvList.add(Arrays.asList(values));
        }

        return csvList;
    }

    public List<String> getHeaders(List<List<String>> csvList) {
        List<String> headers = csvList.get(0);
        return headers;
    }

}
