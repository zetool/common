package event;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ProcessUpdateMessageTest {

    @Test
    public void testData() {
        ProcessUpdateMessage message = new ProcessUpdateMessage(50, "name", "information", "details");
        assertThat(message.progress, is(equalTo(50)));
        assertThat(message.taskName, is(equalTo("name")));
        assertThat(message.taskProgressInformation, is(equalTo("information")));
        assertThat(message.taskDetailedProgressInformation, is(equalTo("details")));
    }

}
