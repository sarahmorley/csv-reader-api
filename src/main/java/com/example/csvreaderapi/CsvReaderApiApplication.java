package com.example.csvreaderapi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CsvReaderApiApplication {

	private static final Logger logger = LoggerFactory.getLogger(CsvReaderApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CsvReaderApiApplication.class, args);
		logger.info("App started successfully.");
	}


}
