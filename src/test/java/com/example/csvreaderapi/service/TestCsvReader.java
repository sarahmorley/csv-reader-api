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
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfigCsvReader.class)
@SpringBootTest(properties = {"file.path=/PracticeFile.csv"})
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
		Mockito.verify(dynamoDbStorage, Mockito.times(5)).save(anyString(), anyInt(), anyList(), anyList());
	}


	@Test
	public void testDynamoCalledForEven() throws IOException{
		List<List<String>> rowListEven = csvReader.loadCsv("EVEN");
		Mockito.verify(dynamoDbStorage, Mockito.times(1)).createTable(anyString());
		Mockito.verify(dynamoDbStorage, Mockito.times(5)).save(anyString(), anyInt(), anyList(), anyList());
	}

	@Test
	public void testDynamoCalledForAll() throws IOException{
		List<List<String>> rowListAll = csvReader.loadCsv("");
		Mockito.verify(dynamoDbStorage, Mockito.times(1)).createTable(anyString());
		Mockito.verify(dynamoDbStorage, Mockito.times(10)).save(anyString(), anyInt(), anyList(), anyList());
	}

	@Test
	public void testNumberOfRowsReturned() throws IOException {
		List<List<String>> rowListOdd = csvReader.loadCsv("ODD");
		int countOddRows = rowListOdd.size();
		assertEquals(5, countOddRows);

		List<List<String>> rowListEven = csvReader.loadCsv("EVEN");
		int countEvenRows = rowListEven.size();
		assertEquals(5, countEvenRows);

		List<List<String>> rowListAll = csvReader.loadCsv("");
		int countAllRows = rowListAll.size();
		assertEquals(10, countAllRows);
	}

	@Test
	public void testOddData() throws IOException {
		List<List<String>> rowListOdd = csvReader.loadCsv("ODD");

		List<String> testDataList = new ArrayList<>();
		for (int i = 0; i < rowListOdd.size()-1; i++) {
			List<String> rowData = rowListOdd.get(i);
			testDataList.add(rowData.get(i));
		}
		assertEquals("1", testDataList.get(0));
		assertEquals("data3", testDataList.get(1));
		assertEquals("data2-5", testDataList.get(2));
		assertEquals("data3-7", testDataList.get(3));
	}

	@Test
	public void testEvenData() throws IOException {
		List<List<String>> rowListEven = csvReader.loadCsv("EVEN");

		List<String> testDataList = new ArrayList<>();
		for (int i = 0; i < rowListEven.size()-1; i++) {
			List<String> rowData = rowListEven.get(i);
			testDataList.add(rowData.get(i));
		}
		assertEquals("2", testDataList.get(0));
		assertEquals("data4", testDataList.get(1));
		assertEquals("data2-6", testDataList.get(2));
		assertEquals("data3-8", testDataList.get(3));
	}

	@Test
	public void testAllData() throws IOException {
		List<List<String>> rowListAll = csvReader.loadCsv("");
		List<String> testDataList = new ArrayList<>();

		for (int i = 0; i <=9; i++){
			List<String> rowDataIncrementing = rowListAll.get(i);
			testDataList.add(rowDataIncrementing.get(2));
		}
		assertEquals("data2-1", testDataList.get(0));
		assertEquals("data2-2", testDataList.get(1));
		assertEquals("data2-3", testDataList.get(2));
		assertEquals("data2-4", testDataList.get(3));
		assertEquals("data2-5", testDataList.get(4));
		assertEquals("data2-6", testDataList.get(5));
		assertEquals("data2-7", testDataList.get(6));
		assertEquals("data2-8", testDataList.get(7));
		assertEquals("data2-9", testDataList.get(8));
		assertEquals("data2-10", testDataList.get(9));

		testDataList.clear();

		for (int i = 9; i >=0; i--){
			List<String> rowDataDecrementing = rowListAll.get(i);
			testDataList.add(rowDataDecrementing.get(1));
		}
		assertEquals("data10", testDataList.get(0));
		assertEquals("data9", testDataList.get(1));
		assertEquals("data8", testDataList.get(2));
		assertEquals("data7", testDataList.get(3));
		assertEquals("data6", testDataList.get(4));
		assertEquals("data5", testDataList.get(5));
		assertEquals("data4", testDataList.get(6));
		assertEquals("data3", testDataList.get(7));
		assertEquals("data2", testDataList.get(8));
		assertEquals("data1", testDataList.get(9));
	}

}
