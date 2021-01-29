package com.example.csvreaderapi.controller;

import com.example.csvreaderapi.CsvReader;
import com.example.csvreaderapi.storage.DynamoDbStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.util.List;
import java.util.Locale;


@RestController
public class RowsController {

    @Autowired
    private CsvReader csvReader;

    @Autowired
    private DynamoDbStorage dynamoDbStorage;

    @GetMapping("/rows")
    public String getRows (@RequestParam String parity) throws IOException {
        parity = parity.toUpperCase(Locale.ROOT);
        List<List<String>> csvList = csvReader.loadCsv(parity);
        List<String> headers = csvReader.getHeaders(csvList);


        String returnVal = "return";
        return returnVal;
    }


}
