package oracle.interview.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonthTest {

    @Test
    public void test_getMonth() {
        String expectedMontForJanuary = "01";
        String expectedMontForNovember = "11";

        String actualMontForJanuary = Month.fromName("Jan").getNumberAsString();
        String actualMontForNovember = Month.fromName("Nov").getNumberAsString();

        assertEquals(expectedMontForJanuary, actualMontForJanuary);
        assertEquals(expectedMontForNovember, actualMontForNovember);
    }

}
