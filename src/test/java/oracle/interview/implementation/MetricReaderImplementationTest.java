package oracle.interview.implementation;

import oracle.interview.metrics.MetricReader;
import oracle.interview.metrics.TargetMetricsContainer;
import oracle.interview.utils.MetricsException;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.List;

import static oracle.interview.metrics.Main.findFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MetricReaderImplementationTest {

    @Test
    public void test_readMetricsWithInputStreamNull() throws IOException, SAXException, ParserConfigurationException {
        String expectedErrorMessage = "InputStream can not be null";
        MetricReader actualReader = new MetricReaderImplementation();

        try {
            actualReader.readMetrics(null);
        } catch (MetricsException e) {
            assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    public void test_readMetricsWithErrorInValue() throws IOException, SAXException, ParserConfigurationException,
            MetricsException {
        String expectedErrorMessage = "For input string: \"l\"";
        MetricReader actualReader = new MetricReaderImplementation();

        try (FileInputStream fis = new FileInputStream(findFile("metrics_data_with_error_in_value.xml"))) {
            actualReader.readMetrics(fis);
        } catch (NumberFormatException e) {
            assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    public void test_readMetricsWithErrorInYear() throws IOException, SAXException, ParserConfigurationException,
            MetricsException {
        String expectedErrorMessage = "Text '20-12-06T00:00:00Z' could not be parsed at index 0";
        MetricReader actualReader = new MetricReaderImplementation();

        try (FileInputStream fis = new FileInputStream(findFile("metrics_data_with_error_in_year.xml"))) {
            actualReader.readMetrics(fis);
        } catch (DateTimeParseException e) {
            assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    public void test_readMetricsWithoutTimestampFiled() throws IOException, SAXException, ParserConfigurationException {
        String expectedErrorMessage = "Attribute timestamp is missing";
        MetricReader actualReader = new MetricReaderImplementation();

        try (FileInputStream fis = new FileInputStream(findFile("metrics_data_without_timestamp_field.xml"))) {
            actualReader.readMetrics(fis);
        } catch (MetricsException e) {
            assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    public void test_readMetricsXmlWellFormed() throws IOException, SAXException, ParserConfigurationException, MetricsException {
        String expectedListContent = "[MetricContainer{targetName='red.oracle.com', targetType='host', " +
                "payload=[{type=cpu, value=80, timestamp=2020-12-10T00:00:00Z}, {type=memory, value=61, " +
                "timestamp=2020-12-10T00:00:00Z}, {type=cpu, value=12, timestamp=2020-12-04T00:00:00Z}, " +
                "{type=memory, value=49, timestamp=2020-12-04T00:00:00Z}]}, MetricContainer{targetName='blue.oracle.com', " +
                "targetType='host', payload=[{type=cpu, value=80, timestamp=2020-12-10T00:00:00Z}, {type=memory, " +
                "value=55, timestamp=2020-12-10T00:00:00Z}, {type=cpu, value=20, timestamp=2020-12-09T00:00:00Z}, " +
                "{type=memory, value=50, timestamp=2020-12-09T00:00:00Z}]}]";
        MetricReader actualReader = new MetricReaderImplementation();

        List<TargetMetricsContainer> actualListContent;
        try (FileInputStream fis = new FileInputStream(findFile("metrics_data_well_formed.xml"))) {
            actualListContent = actualReader.readMetrics(fis);
        }

        assertNotNull(actualListContent);
        assertEquals(expectedListContent, actualListContent.toString());
    }

}
