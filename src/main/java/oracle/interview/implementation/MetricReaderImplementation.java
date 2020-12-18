package oracle.interview.implementation;

import oracle.interview.metrics.MetricReader;
import oracle.interview.metrics.TargetMetricsContainer;
import oracle.interview.utils.MetricsException;
import oracle.interview.utils.Utils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MetricReaderImplementation implements MetricReader {
    @Override
    public List<TargetMetricsContainer> readMetrics(InputStream metricInputStream)
            throws ParserConfigurationException, SAXException, IOException, MetricsException {
        // TODO: implement this, reading data from the input stream, returning a list of containers read from the stream
        Document document = buildDocumentFromInputStream(metricInputStream);
        List<TargetMetricsContainer> targetList = buildTargetMetricsList(document);

        return targetList;
    }

    /**
     * Read an InputStream and return it as Document Object.
     */
    private Document buildDocumentFromInputStream(InputStream fis) throws SAXException, IOException, MetricsException,
            ParserConfigurationException, NumberFormatException, StringIndexOutOfBoundsException {
        if(fis == null)
            throw new MetricsException("InputStream can not be null");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        return documentBuilder.parse(fis);
    }

    /**
     * Build a TargetMetricsContainer List from Document object containing all the XML information
     *
     * @param document
     * @return List<TargetMetricsContainer>
     * @throws NumberFormatException
     * @throws StringIndexOutOfBoundsException
     */
    private List<TargetMetricsContainer> buildTargetMetricsList(Document document)
            throws NumberFormatException, MetricsException {
        List<TargetMetricsContainer> targetList = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName(TARGET_NODE_TYPE);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                //Getting target information
                TargetMetricsContainer targetMetricsContainer = new TargetMetricsContainer(
                        element.getAttribute(METRIC_NAME_ATTRIBUTE),
                        element.getAttribute(METRIC_TYPE_ATTRIBUTE));

                NodeList nodeTargetList = nodeList.item(i).getChildNodes();

                for (int j = 0; j < nodeTargetList.getLength(); j++) {
                    Node nodeTarget = nodeTargetList.item(j);

                    if (nodeTarget.getNodeType() == Node.ELEMENT_NODE) {
                        if(!nodeTarget.getNodeName().equals(METRIC_DESCRIPTION_ATTRIBUTE)) {
                            //Getting metrics information
                            NamedNodeMap metricsAttributes = nodeTarget.getAttributes();

                            targetMetricsContainer.addMetric(
                                    getValueFromAttribute(metricsAttributes, METRIC_TYPE_ATTRIBUTE),
                                    Utils.parseStringToInstant(
                                            getValueFromAttribute(metricsAttributes, METRIC_TIMESTAMP_ATTRIBUTE)
                                    ),
                                    Integer.parseInt(
                                            getValueFromAttribute(metricsAttributes, METRIC_VALUE_ATTRIBUTE)
                                    )
                            );
                        }
                    }
                }

                targetList.add(targetMetricsContainer);
            }
        }

        return targetList;
    }

    /**
     * Ensure to get a valid attribute name, or else return a MetricsException indicating the wrong/missing attribute
     *
     * @param attribute
     * @param attributeName
     * @return
     * @throws MetricsException
     */
    private String getValueFromAttribute(NamedNodeMap attribute, String attributeName) throws MetricsException {
        String attributeValue = null;
        try {
            attributeValue = attribute.getNamedItem(attributeName).getNodeValue();
            if(attributeValue == null)
                throw new NullPointerException(attributeName);
        } catch (NullPointerException e) {
            throw new MetricsException("Attribute " + attributeName + " is missing");
        }

        return attributeValue;
    }

}
