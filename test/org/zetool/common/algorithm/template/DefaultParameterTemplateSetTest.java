package org.zetool.common.algorithm.template;

import java.util.Collections;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class DefaultParameterTemplateSetTest {
    @Test
    public void initializeEmpty() {
        DefaultParameterTemplateSet set = new DefaultParameterTemplateSet(Collections.emptySet());
        
        assertThat(set.isEmpty(), is(true));
        assertThat(set.contains(mock(ParameterTemplate.class)), is(false));
        
        ParameterTemplate template = mock(ParameterTemplate.class, "mock");
        set = new DefaultParameterTemplateSet(Collections.singleton(template));
        assertThat(set.isEmpty(), is(false));
        assertThat(set.contains(template), is(true));
    }

    @Test
    public void builderTest() {
        ParameterTemplateSet set = new DefaultParameterTemplateSet.Builder().build();
        assertThat(set.isEmpty(), is(true));        
    }

    @Test
    public void builderWithIntegerTest() {
        ParameterTemplateSet set = new DefaultParameterTemplateSet.Builder().withParameter("integer", "integerTest", 3).build();
        assertThat(set.isEmpty(), is(false));
        ParameterTemplate<Integer> intParameter = (ParameterTemplate<Integer>) set.iterator().next();
        assertThat(intParameter.getName(), is(equalTo("integer")));
        assertThat(intParameter.getDescription(), is(equalTo("integerTest")));
        assertThat(intParameter, contains(3));
    }
}
