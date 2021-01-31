package com.example.csvreaderapi.service;

import com.example.csvreaderapi.configuration.TestConfigCsvReader;
import com.example.csvreaderapi.storage.DynamoDbStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfigCsvReader.class)
@SpringBootTest(properties = {"file.path=/TestFile.csv"})
public class TestCsvReader {

	@Autowired
	private CsvReader csvReader;

	@Autowired
	private DynamoDbStorage dynamoDbStorage;

	@Before
	public void testSetup() {
		Mockito.reset(dynamoDbStorage);
	}

	@Test
	public void testDynamoCalledForOdd() throws IOException{
		List<List<String>> rowListOdd = csvReader.loadCsv("ODD");
		Mockito.verify(dynamoDbStorage, Mockito.times(1)).createTable(anyString());
		Mockito.verify(dynamoDbStorage, Mockito.times(2)).save(anyString(), anyInt(), anyList(), anyList());
	}

	@Test
	public void testDynamoCalledForEven() throws IOException{
		List<List<String>> rowListEven = csvReader.loadCsv("EVEN");
		Mockito.verify(dynamoDbStorage, Mockito.times(1)).createTable(anyString());
		Mockito.verify(dynamoDbStorage, Mockito.times(2)).save(anyString(), anyInt(), anyList(), anyList());
	}

	@Test
	public void testDynamoCalledForAll() throws IOException{
		List<List<String>> rowListAll = csvReader.loadCsv("");
		Mockito.verify(dynamoDbStorage, Mockito.times(1)).createTable(anyString());
		Mockito.verify(dynamoDbStorage, Mockito.times(4)).save(anyString(), anyInt(), anyList(), anyList());
	}

	@Test
	public void testOddData() throws IOException {
		List<List<String>> actaulRowListOdd = csvReader.loadCsv("ODD");
		List<List<String>> expectedRowListOdd = new ArrayList<>();
		List<String> rowsToAddOne = Arrays.asList("1","data1","data2-1","data3-1");
		List<String> rowsToAddTwo = Arrays.asList("3","data3","data2-3","data3-3");
		expectedRowListOdd.add(rowsToAddOne);
		expectedRowListOdd.add(rowsToAddTwo);
		assertEquals(expectedRowListOdd, actaulRowListOdd);
	}

	@Test
	public void testEvenData() throws IOException {
		List<List<String>> actualRowListEven = csvReader.loadCsv("EVEN");
		List<List<String>> expectedRowListEven = new ArrayList<>();
		List<String> rowsToAddOne = Arrays.asList("2","data2","data2-2","data3-2");
		List<String> rowsToAddTwo = Arrays.asList("4","data4","data2-4","data3-4");
		expectedRowListEven.add(rowsToAddOne);
		expectedRowListEven.add(rowsToAddTwo);
		assertEquals(expectedRowListEven, actualRowListEven);
	}

	@Test
	public void testAllData() throws IOException {
		List<List<String>> actualRowListAll = csvReader.loadCsv("");
		List<List<String>> expectedRowListAll = new ArrayList<>();
		List<String> rowsToAddOne = Arrays.asList("1","data1","data2-1","data3-1");
		List<String> rowsToAddTwo = Arrays.asList("2","data2","data2-2","data3-2");
		List<String> rowsToAddThree = Arrays.asList("3","data3","data2-3","data3-3");
		List<String> rowsToAddFour = Arrays.asList("4","data4","data2-4","data3-4");
		expectedRowListAll.add(rowsToAddOne);
		expectedRowListAll.add(rowsToAddTwo);
		expectedRowListAll.add(rowsToAddThree);
		expectedRowListAll.add(rowsToAddFour);
		assertEquals(expectedRowListAll, actualRowListAll);
	}

}
