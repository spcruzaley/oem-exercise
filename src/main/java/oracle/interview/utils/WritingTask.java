package oracle.interview.utils;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public interface WritingTask {
    void run() throws ExecutionException, SQLException;
}
