package com.example.csvreaderapi.controller;

import com.example.csvreaderapi.exception.InvalidQueryParamException;
import com.example.csvreaderapi.service.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.util.*;

@RestController
public class RowsController {

    final String odd = "ODD";
    final String even = "EVEN";

    @Autowired
    private CsvReader csvReader;

    @GetMapping("/rows")
    public List<List<String>> getRows (@RequestParam(required = false) String parity) throws IOException {
        if(parity == null)
            parity = "";

        parity = parity.toUpperCase();
        List<String> validParams = Arrays.asList(odd, even, "");

        if(!validParams.contains(parity)){
            throw new InvalidQueryParamException();
        }

        List<List<String>> csvList = csvReader.loadCsv(parity);

        return csvList;
    }


}
