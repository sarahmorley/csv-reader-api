package it;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class CsvReaderApiIT {

    @Test
    public void testOddRows() {
        assertEquals("hello", "hello");
    }

}
