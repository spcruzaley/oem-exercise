package oracle.interview.metrics;

import oracle.interview.utils.MetricsException;

public interface MetricWriter {

    void writeMetricsContainer(TargetMetricsContainer metricsContainer) throws MetricsException;

}
