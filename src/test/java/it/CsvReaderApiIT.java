package it;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import okhttp3.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class CsvReaderApiIT {
    private static String url = "http://localhost:8080/rows";

    public CsvReaderApiIT() throws IOException {
    }

    @Test
    public void testOddRows() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url + "?parity=odd")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        List<List<String>> expectedRowsOdd = new ArrayList<>();
        List<String> rowsToAddOne = Arrays.asList("1","data1","data2-1","data3-1");
        List<String> rowsToAddTwo = Arrays.asList("3","data3","data2-3","data3-3");
        List<String> rowsToAddThree = Arrays.asList("5","data5","data2-5","data3-5");
        List<String> rowsToAddFour = Arrays.asList("7","data7","data2-7","data3-7");
        List<String> rowsToAddFive = Arrays.asList("9","data9","data2-9","data3-9");
        expectedRowsOdd.add(rowsToAddOne);
        expectedRowsOdd.add(rowsToAddTwo);
        expectedRowsOdd.add(rowsToAddThree);
        expectedRowsOdd.add(rowsToAddFour);
        expectedRowsOdd.add(rowsToAddFive);

        String responseData = response.body().string();
        Object obj= JSONValue.parse(responseData);
        JSONArray actualRowsOdd = (JSONArray)obj;
        assertEquals(expectedRowsOdd, actualRowsOdd);
    }

    @Test
    public void testEvenRows() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url + "?parity=even")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        List<List<String>> expectedRowsEven = new ArrayList<>();
        List<String> rowsToAddOne = Arrays.asList("2","data2","data2-2","data3-2");
        List<String> rowsToAddTwo = Arrays.asList("4","data4","data2-4","data3-4");
        List<String> rowsToAddThree = Arrays.asList("6","data6","data2-6","data3-6");
        List<String> rowsToAddFour = Arrays.asList("8","data8","data2-8","data3-8");
        List<String> rowsToAddFive = Arrays.asList("10","data10","data2-10","data3-10");
        expectedRowsEven.add(rowsToAddOne);
        expectedRowsEven.add(rowsToAddTwo);
        expectedRowsEven.add(rowsToAddThree);
        expectedRowsEven.add(rowsToAddFour);
        expectedRowsEven.add(rowsToAddFive);

        String responseData = response.body().string();
        Object obj= JSONValue.parse(responseData);
        JSONArray actualRowsEven = (JSONArray)obj;
        assertEquals(expectedRowsEven, actualRowsEven);
    }

    @Test
    public void testAllRows() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url + "?parity=")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        List<List<String>> expectedRowsAll = new ArrayList<>();
        List<String> rowsToAddOne = Arrays.asList("1","data1","data2-1","data3-1");
        List<String> rowsToAddTwo = Arrays.asList("2","data2","data2-2","data3-2");
        List<String> rowsToAddThree = Arrays.asList("3","data3","data2-3","data3-3");
        List<String> rowsToAddFour = Arrays.asList("4","data4","data2-4","data3-4");
        List<String> rowsToAddFive = Arrays.asList("5","data5","data2-5","data3-5");
        List<String> rowsToAddSix = Arrays.asList("6","data6","data2-6","data3-6");
        List<String> rowsToAddSeven = Arrays.asList("7","data7","data2-7","data3-7");
        List<String> rowsToAddEight = Arrays.asList("8","data8","data2-8","data3-8");
        List<String> rowsToAddNine = Arrays.asList("9","data9","data2-9","data3-9");
        List<String> rowsToAddTen = Arrays.asList("10","data10","data2-10","data3-10");
        expectedRowsAll.add(rowsToAddOne);
        expectedRowsAll.add(rowsToAddTwo);
        expectedRowsAll.add(rowsToAddThree);
        expectedRowsAll.add(rowsToAddFour);
        expectedRowsAll.add(rowsToAddFive);
        expectedRowsAll.add(rowsToAddSix);
        expectedRowsAll.add(rowsToAddSeven);
        expectedRowsAll.add(rowsToAddEight);
        expectedRowsAll.add(rowsToAddNine);
        expectedRowsAll.add(rowsToAddTen);

        String responseData = response.body().string();
        Object obj= JSONValue.parse(responseData);
        JSONArray actualRowsAll = (JSONArray)obj;
        assertEquals(expectedRowsAll, actualRowsAll);
    }

    @Test
    public void testErrorValidationsOnWrongQueryParam() throws IOException {
        String exceptionMessage = "The parameter you passed is invalid. Please pass 'ODD', 'EVEN' or else do not supply a parameter";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url + "?parity=Foo")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(400, response.code());
        assertEquals(exceptionMessage, response.body().string());


    }



}
