package org.zetool.common.algorithm.template;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class BooleanParameterTemplateTest {
    @Test
    public void initialization() {
        BooleanParameterTemplate defaultFalseParameter = new BooleanParameterTemplate("parameter", "with description", Boolean.FALSE);
        BooleanParameterTemplate defaultTrueParameter = new BooleanParameterTemplate("another parameter", "with description", Boolean.TRUE);
        
        assertThat(defaultFalseParameter.getDefault(), is(equalTo(false)));
        assertThat(defaultTrueParameter.getDefault(), is(equalTo(true)));
        assertThat(defaultFalseParameter.getDescription(), is(equalTo("with description")));
        assertThat(defaultFalseParameter.getName(), is(equalTo("parameter")));
        assertThat(defaultFalseParameter, containsInAnyOrder(false, true));
        assertThat(defaultFalseParameter.numberOfValues(), is(equalTo(2)));
    }
    
    @Test
    public void constantBooleanParameters() {
        assertThat(BooleanParameterTemplate.constant("some", "desc", Boolean.FALSE), contains(false));
        assertThat(BooleanParameterTemplate.constant("some", "desc", Boolean.FALSE).numberOfValues(), is(equalTo(1)));
        assertThat(BooleanParameterTemplate.constant("some", "desc", Boolean.TRUE), contains(true));
    }
}
