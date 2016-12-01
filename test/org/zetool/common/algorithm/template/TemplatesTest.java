package org.zetool.common.algorithm.template;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TemplatesTest {

    ParameterTemplateSet emptyParameters = Templates.emptyParameters();

    @Test
    public void a() {
        assertThat(emptyParameters.isEmpty(), is(equalTo(true)));
        assertThat(emptyParameters.contains(mock(ParameterTemplate.class)), is(false));
        assertThat(emptyParameters.iterator().hasNext(), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void retrieveValueFails() {
        emptyParameters.getValue(mock(ParameterTemplate.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateFails() {
        emptyParameters.update(mock(ParameterTemplate.class), new Object());
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValidFails() {
        emptyParameters.isChangeValid(mock(ParameterTemplate.class), new Object());
    }
}
