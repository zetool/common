package event;

import org.junit.Test;
import static org.zetool.test.StandardAssert.assertDataAccess;

public class ProgressEventTest {

    @Test
    public void testData() {
        Object source = new Object();
        ProcessUpdateMessage message = new ProcessUpdateMessage(50, "name", "information", "details");
        ProgressEvent<Object> progressEvent = new ProgressEvent<>(source, message);

        assertDataAccess(progressEvent, ProgressEvent::getSource, source);
        assertDataAccess(progressEvent, ProgressEvent::getProcessMessage, message);
    }

}
