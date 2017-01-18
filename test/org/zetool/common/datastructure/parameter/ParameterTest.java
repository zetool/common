package org.zetool.common.datastructure.parameter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ParameterTest {
    
    @Test
    public void parameterTest() {
        Parameter<Integer> parameter = new Parameter<>("intParam", 3);
        assertThat(parameter.getName(), is(equalTo("intParam")));
        assertThat(parameter.getValue(), is(equalTo(3)));
    }
    
}
