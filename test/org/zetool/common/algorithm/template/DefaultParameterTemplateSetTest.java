package org.zetool.common.algorithm.template;

import java.util.Collections;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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
        assertTemplateSet("integer", "integerTest", intParameter, 3);
    }

    @Test
    public void builderWithIntegerRange() {
        ParameterTemplateSet set = new DefaultParameterTemplateSet.Builder().withParameter("integer", "integerTest", 3, 2, 5).build();
        assertThat(set.isEmpty(), is(false));
        ParameterTemplate<Integer> intParameter = (ParameterTemplate<Integer>) set.iterator().next();
        assertTemplateSet("integer", "integerTest", intParameter, 3);
        boundFails(intParameter, 0, 2, 3, 4, 6);
    }

    @Test
    public void builderWithIntegerInvalidRange() {
        ParameterTemplateSet set = new DefaultParameterTemplateSet.Builder().withParameter("integer", "integerTest", 3, "2", 5).build();
        assertThat(set.isEmpty(), is(false));
        ParameterTemplate<Integer> intParameter = (ParameterTemplate<Integer>) set.iterator().next();
        assertTemplateSet("integer", "integerTest", intParameter, 3);
        allSuccess(intParameter, 0, 2, 3, 4, 6);

        set = new DefaultParameterTemplateSet.Builder().withParameter("integer", "integerTest", 3, 2, "5").build();
        assertThat(set.isEmpty(), is(false));
        intParameter = (ParameterTemplate<Integer>) set.iterator().next();
        assertTemplateSet("integer", "integerTest", intParameter, 3);
        allSuccess(intParameter, 0, 2, 3, 4, 6);
    }
    
    @Test
    public void builderWithDouble() {
        ParameterTemplateSet set = new DefaultParameterTemplateSet.Builder().withParameter("double", "doubleTest", 3.14).build();
        assertThat(set.isEmpty(), is(false));
        ParameterTemplate<Double> doubleParameter = (ParameterTemplate<Double>) set.iterator().next();
        assertTemplateSet("double", "doubleTest", doubleParameter, 3.14);
    }


    @Test
    public void builderWithDoubleRange() {
        ParameterTemplateSet set = new DefaultParameterTemplateSet.Builder().withParameter("double", "doubleTest", 3.14, 2.0, 5.0).build();
        assertThat(set.isEmpty(), is(false));
        ParameterTemplate<Double> doubleParameter = (ParameterTemplate<Double>) set.iterator().next();
        assertTemplateSet("double", "doubleTest", doubleParameter, 3.14);
        boundFails(doubleParameter, 0.0, 2.0, 3.0, 4.0, 6.0);
    }

    @Test
    public void builderWithDoubleInvalidRange() {
        ParameterTemplateSet set = new DefaultParameterTemplateSet.Builder().withParameter("double", "doubleTest", 3.14, "2.0", 5.0).build();
        assertThat(set.isEmpty(), is(false));
        ParameterTemplate<Double> doubleParameter = (ParameterTemplate<Double>) set.iterator().next();
        assertTemplateSet("double", "doubleTest", doubleParameter, 3.14);
        allSuccess(doubleParameter, 0.0, 2.0, 3.0, 4.0, 6.0);

        set = new DefaultParameterTemplateSet.Builder().withParameter("double", "doubleTest", 3.14, 2.0, "5.0").build();
        assertThat(set.isEmpty(), is(false));
        doubleParameter = (ParameterTemplate<Double>) set.iterator().next();
        assertTemplateSet("double", "doubleTest", doubleParameter, 3.14);
        allSuccess(doubleParameter, 0.0, 2.0, 3.0, 4.0, 6.0);
    }

    @Test
    public void builderWithStringTest() {
        ParameterTemplateSet set = new DefaultParameterTemplateSet.Builder().withParameter("string", "stringTest", "example").build();
        assertThat(set.isEmpty(), is(false));
        ParameterTemplate<String> stringParameter = (ParameterTemplate<String>) set.iterator().next();
        assertTemplateSet("string", "stringTest", stringParameter, "example");
    }
    
    private <T> void assertTemplateSet(String name, String description, ParameterTemplate<T> parameter, T value) {
        assertThat(parameter.getName(), is(equalTo(name)));
        assertThat(parameter.getDescription(), is(equalTo(description)));
        assertThat(parameter, contains(value));
    }

    private  <T> void allSuccess(ParameterTemplate<T> parameter, T... values) {
        for(T value : values) {
            assertThat(parameter.isValid(value), is(equalTo(ValidationResult.SUCCESS)));
        }
    }

    private  <T> void boundFails(ParameterTemplate<T> parameter, T... values) {
        assertThat(parameter.isValid(values[0]), is(not(equalTo(ValidationResult.SUCCESS))));
        for( int i = 1; i < values.length - 1; ++i) {
            assertThat(parameter.isValid(values[i]), is(equalTo(ValidationResult.SUCCESS)));
        }
        assertThat(parameter.isValid(values[values.length - 1]), is(not(equalTo(ValidationResult.SUCCESS))));
    }
}
