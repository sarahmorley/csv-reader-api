package com.example.csvreaderapi.controller;

import com.example.csvreaderapi.exception.InvalidQueryParamException;
import com.example.csvreaderapi.service.CsvReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.util.*;

@RestController
public class RowsController {

    @Autowired
    private CsvReaderService csvReaderService;

    final List<String> validParams = Arrays.asList(CsvReaderService.odd, CsvReaderService.even, "");

    @GetMapping("/rows")
    public List<List<String>> getRows (@RequestParam(required = false) String parity) throws IOException {
        if(parity == null)
            parity = "";

        parity = parity.toUpperCase();

        if(!validParams.contains(parity)){
            throw new InvalidQueryParamException();
        }

        List<List<String>> csvList = csvReaderService.loadCsv(parity);

        return csvList;
    }


}
