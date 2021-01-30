package com.example.csvreaderapi.controller;

import com.example.csvreaderapi.service.CsvReader;
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

    @GetMapping("/rows")
    public List<List<String>> getRows (@RequestParam String parity) throws IOException {
        parity = parity.toUpperCase();
        List<List<String>> csvList = csvReader.loadCsv(parity);

        return csvList;
    }


}
