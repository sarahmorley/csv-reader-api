package com.example.csvreaderapi.controller;

import com.example.csvreaderapi.CsvReader;
import com.example.csvreaderapi.storage.DynamoDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.util.List;


@RestController
public class RowsController {

    @Autowired
    private CsvReader csvReader;

    @Autowired
    private DynamoDb dynamoDb;

    @GetMapping("/rows")
    public String getRows (@RequestParam String parity) throws IOException {

        List<List<String>> csvList = csvReader.loadCsv();
        List<String> headers = csvReader.getHeaders(csvList);

        dynamoDb.createTable(headers, "SarahTable");


        String returnVal = "return";
        return returnVal;
    }


}
