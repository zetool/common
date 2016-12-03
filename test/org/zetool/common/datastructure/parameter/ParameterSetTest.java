package org.zetool.common.datastructure.parameter;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.zetool.common.algorithm.template.ParameterTemplate;
import org.zetool.common.algorithm.template.ParameterTemplateSet;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ParameterSetTest {

    @Test
    public void emptyParameterTest() {
        ParameterSet empty = ParameterSet.emptyParameterSet();
        assertThat(empty.isEmpty(), is(equalTo(true)));
        assertThat(empty.iterator().hasNext(), is(equalTo(false)));
        assertThat(empty.size(), is(equalTo(0)));
    }

    @Test
    public void templatesToParameterSet() {
        ParameterTemplateSet pts = mock(ParameterTemplateSet.class);
        ParameterTemplate<Integer> i = mock(ParameterTemplate.class);
        ParameterTemplate<String> s = mock(ParameterTemplate.class);

        List<ParameterTemplate<?>> parameters = Arrays.asList(i, s);
        when(pts.iterator()).thenReturn(parameters.iterator());

        when(pts.getValue(i)).thenReturn(3);
        when(pts.getValue(s)).thenReturn("text");

        Parameter<Integer> intParam = mock(Parameter.class);
        Parameter<String> stringParam = mock(Parameter.class);

        when(i.getParameter(3)).thenReturn(intParam);
        when(s.getParameter("text")).thenReturn(stringParam);

        ParameterSet ps = new ParameterSet(pts);

        assertThat(ps.size(), is(equalTo(2)));
        assertThat(ps.isEmpty(), is(equalTo(false)));

        List<Parameter<?>> params = new LinkedList<>();
        Iterator<Parameter<?>> paramIterator = ps.iterator();
        while (paramIterator.hasNext()) {
            params.add(paramIterator.next());
        }
        assertThat(params, contains(intParam, stringParam));
    }
}
