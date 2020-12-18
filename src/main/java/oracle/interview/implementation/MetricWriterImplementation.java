package oracle.interview.implementation;

import oracle.interview.metrics.MetricStorage;
import oracle.interview.metrics.MetricWriter;
import oracle.interview.metrics.TargetMetricsContainer;
import oracle.interview.utils.MetricsException;
import oracle.interview.utils.WritingTask;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class MetricWriterImplementation implements MetricWriter {

    private final MetricStorage storage;
    private static final Logger log = Logger.getLogger(MetricWriterImplementation.class.getName());

    public MetricWriterImplementation(MetricStorage storage) {
        this.storage = storage;
    }

    @Override
    public void writeMetricsContainer(TargetMetricsContainer metricsContainer) throws MetricsException {
        // TODO: write this metricsContainer to the MetricStorage. Hint
        //      storage.write(metricsContainer);
        //  partially works.  Since the write could fail, retry the write on failure
        //  as appropriate.

        boolean execution = false;

        try {
            execution = runWithRetries(5, () -> {
                storage.write(metricsContainer);
            });
        } catch (NullPointerException e) {
            throw new MetricsException("Something went wrong with TargetMetricsContainer Object data");
        }

        if(!execution)
            throw new MetricsException("Exceeded the maximum amount of tries to execute the write for target: " +
                    metricsContainer.getTargetName());
    }

    /**
     * Method to execute a task a certain times in case it fails, after that will to send an exception
     *
     * @param maxRetries
     * @param task
     * @return
     */
    private boolean runWithRetries(int maxRetries, WritingTask task) throws NullPointerException {
        int count = 0;
        while (count < maxRetries) {
            try {
                task.run();
                return true;
            }
            catch (ExecutionException | SQLException e) {
                log.info("[Try "+ count+"] Error to write information to DB, retrying...");
                if (++count >= maxRetries) {
                    return false;
                }
            }
        }

        return false;
    }

}