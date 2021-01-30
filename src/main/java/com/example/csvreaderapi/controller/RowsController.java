package com.example.csvreaderapi.controller;

import com.example.csvreaderapi.service.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @GetMapping("/rows")
    public List<List<String>> getRows (@RequestParam(required = false) String parity) throws IOException {
        if(parity != null)
            parity = parity.toUpperCase();

        if((parity != null && !parity.equals("") && !parity.equals("EVEN")) && (parity != null && !parity.equals("") && !parity.equals("ODD"))){
            throw new IllegalArgumentException();
        }


        List<List<String>> csvList = csvReader.loadCsv(parity);

        return csvList;
    }


}
