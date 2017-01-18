package org.zetool.common.algorithm.template;

import java.util.Arrays;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ParameterAssignmentMapTest {
    
    private final static ParameterTemplateSet PARAMETER_TEMPLATE_SET = mock(ParameterTemplateSet.class);
    private final static ParameterTemplate<Object> PARAMETER_1 = mock(ParameterTemplate.class, "p1");
    private final static ParameterTemplate<Object> PARAMETER_2 = mock(ParameterTemplate.class, "p2");
    private final ParameterAssignmentMap pam = new ParameterAssignmentMap(PARAMETER_TEMPLATE_SET);
    
    @BeforeClass
    public static void init() {
        ParameterTemplate<?>[] t = new ParameterTemplate<?>[]{PARAMETER_1, PARAMETER_2};
        when(PARAMETER_TEMPLATE_SET.iterator()).thenReturn(Arrays.asList(t).iterator());
        when(PARAMETER_1.isValid(any(Object.class))).thenReturn(ValidationResult.SUCCESS);
        when(PARAMETER_2.isValid(any(Object.class))).thenReturn(ValidationResult.FAILURE);
    }
    
    @Test
    public void isValidSuccesful() {
        assertThat(pam.isChangeValid(PARAMETER_1, new Object()), is(equalTo(ValidationResult.SUCCESS)));
    }

    @Test
    public void isValidFails() {
        assertThat(pam.isChangeValid(PARAMETER_2, new Object()), is(equalTo(ValidationResult.FAILURE)));
    }
    
    @Test
    public void containsWorks() {
        assertThat(pam.contains(PARAMETER_1), is(equalTo(true)));
        assertThat(pam.contains(PARAMETER_2), is(equalTo(true)));
        assertThat(pam.contains((ParameterTemplate<Object>)mock(ParameterTemplate.class)), is(equalTo(false)));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void retrieveValueFails() {
        pam.getValue(mock(ParameterTemplate.class));
    }
}
