package org.zetool.common.function;

import static org.zetool.common.function.Functions.sinkConsumer;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class FunctionsTest {

    @Test
    public void sinkConsumerAcceptsNull() {
        sinkConsumer(new Object());
        sinkConsumer(null);
    }    
}
