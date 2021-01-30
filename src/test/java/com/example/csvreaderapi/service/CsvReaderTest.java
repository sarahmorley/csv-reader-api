package com.example.csvreaderapi.service;

import com.example.csvreaderapi.configuration.TestConfig;
import com.example.csvreaderapi.service.CsvReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class CsvReaderTest {

	@Autowired
	private CsvReader csvReader;

	@Test
	public void contextLoads() throws IOException {
		csvReader.loadCsv("odd");
	}

}
