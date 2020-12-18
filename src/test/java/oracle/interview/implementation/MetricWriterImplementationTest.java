package oracle.interview.implementation;

import oracle.interview.metrics.MetricReader;
import oracle.interview.metrics.MetricWriter;
import oracle.interview.metrics.RandomlyFailingMetricStorage;
import oracle.interview.metrics.TargetMetricsContainer;
import oracle.interview.utils.MetricsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static oracle.interview.metrics.Main.findFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetricWriterImplementationTest {
    static RandomlyFailingMetricStorage storage;

    @BeforeAll
    public static void init() {
        try (RandomlyFailingMetricStorage storageInit = new RandomlyFailingMetricStorage()) {
            storage = storageInit;
        }
    }

    @Test
    public void test_writeMetricsContainerWithNullMetricStorageObject() {
        String expectedExceptionMessage = "Something went wrong with TargetMetricsContainer Object data";

        try {
            MetricWriter actualMetricWriter = new MetricWriterImplementation(null);
            TargetMetricsContainer actualTargetMetricsContainer = new TargetMetricsContainer("", "");
            actualMetricWriter.writeMetricsContainer(actualTargetMetricsContainer);
        } catch (NullPointerException | MetricsException e) {
            assertEquals(expectedExceptionMessage, e.getMessage());
        }
    }

    @Test
    public void test_writeMetricsContainerWithNullTargetMetricsContainer() {
        String expectedExceptionMessage = "Something went wrong with TargetMetricsContainer Object data";

        try {
            MetricWriter actualMetricWriter = new MetricWriterImplementation(storage);
            actualMetricWriter.writeMetricsContainer(null);
        } catch (NullPointerException | MetricsException e) {
            assertEquals(expectedExceptionMessage, e.getMessage());
        }
    }

    @Test
    public void test_writeMetricsContainerHappyPath() throws MetricsException, IOException,
            ParserConfigurationException, SAXException {
        MetricReader actualReader = new MetricReaderImplementation();
        List<TargetMetricsContainer> actualListContent;
        try (FileInputStream fis = new FileInputStream(findFile("metrics_data_well_formed.xml"))) {
            actualListContent = actualReader.readMetrics(fis);
        }
        MetricWriter actualMetricWriter = new MetricWriterImplementation(storage);
        for (TargetMetricsContainer targetMetrics : actualListContent) {
            actualMetricWriter.writeMetricsContainer(targetMetrics);
        }
    }

}
