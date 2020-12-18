package oracle.interview.utils;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {

    @Test
    public void test_stringToTimestampWithDayGreaterThan9() {
        String inputFormat = "17-Dec-2020";
        String expectedDate = "2020-12-17T00:00:00Z";

        Instant actualInstant = Utils.parseStringToInstant(inputFormat);

        assertEquals(expectedDate, actualInstant.toString());
    }

    @Test
    public void test_stringToTimestampWithDayLessThan10() {
        String inputFormat = "1-Dec-2020";
        String expectedDate = "2020-12-01T00:00:00Z";

        Instant actualInstant = Utils.parseStringToInstant(inputFormat);

        assertEquals(expectedDate, actualInstant.toString());
    }

    @Test
    public void test_stringToTimestampStringIndexOutOfBoundsException() {
        String inputFormat = "1-Dec-20";
        String expectedExceptionMessage = "Text '20-12-01T00:00:00Z' could not be parsed at index 0";

        try {
            Utils.parseStringToInstant(inputFormat);
        } catch (DateTimeParseException e) {
            assertEquals(expectedExceptionMessage, e.getMessage());
        }
    }

}
