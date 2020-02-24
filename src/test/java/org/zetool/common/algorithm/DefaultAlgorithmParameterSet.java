package org.zetool.common.algorithm;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.zetool.common.datastructure.parameter.ParameterSet;

/**
 *
 * @author Jan-Philipp Kappeier
 */
public class DefaultAlgorithmParameterSet {

    private static class DefaultAlgorithm implements Algorithm<Object, Object> {

        @Override
        public Object getProblem() {
            throw new AssertionError();
        }

        @Override
        public void setProblem(Object problem) {
            throw new AssertionError();
        }

        @Override
        public Object getSolution() {
            throw new AssertionError();
        }

        @Override
        public boolean isRunning() {
            throw new AssertionError();
        }

        @Override
        public void run() {
            throw new AssertionError();
        }

        @Override
        public Object call() {
            throw new AssertionError();
        }
    };

    @Test
    public void defaultParameterNotAccepted() {
        Algorithm<Object, Object> a = new DefaultAlgorithm();
        assertThat(a.acceptsParameters(ParameterSet.emptyParameterSet()), is(equalTo(false)));
        assertThat(a.getParameters(), is(not(nullValue())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void noParametersAccepted() {
        Algorithm<Object, Object> a = new DefaultAlgorithm();
        a.setParameters(ParameterSet.emptyParameterSet());
    }
}
