package org.zetool.common.algorithm.template;

import java.util.Arrays;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ValidatingParameterAssignmentMapTest {
    private static class ConcreteParameterAssignmentMap extends ValidatingParameterAssignmentMap {

        private final ParameterTemplate<Integer> modifiable;
        private final ParameterTemplate<Integer> unmodifiable;

        public ConcreteParameterAssignmentMap(ParameterTemplate<Integer> p1, ParameterTemplate<Integer> p2) {
            super(new DefaultParameterTemplateSet(Arrays.stream(new ParameterTemplate<?>[] {p1, p2}).collect(Collectors.toSet())));
            this.modifiable = p1;
            this.unmodifiable = p2;
        }
        
        @Override
        <T> ValidationResult validate(ParameterTemplate<T> template, T value) {
            if (template == modifiable) {
                return ValidationResult.SUCCESS;
            } else {
                return ValidationResult.FAILURE;
            }
        }
    }
            
    @Test
    public void validatingCircle() {
        ParameterTemplate<Integer> p1 = new IntegerParameterTemplate("", "", 3, 1, 4);
        ParameterTemplate<Integer> p2 = new IntegerParameterTemplate("", "", 2, 1, 4);
        ValidatingParameterAssignmentMap map = new ConcreteParameterAssignmentMap(p1, p2);
        
        // p1 can me modified, but only in the valid range
        assertThat(map.isChangeValid(p1, 2), is(equalTo(ValidationResult.SUCCESS)));
        assertThat(map.isChangeValid(p1, 5), is(not(equalTo(ValidationResult.SUCCESS))));
        
        // p2 can not be modified in whatever range
        assertThat(map.isChangeValid(p2, 3), is(equalTo(ValidationResult.FAILURE)));
        assertThat(map.isChangeValid(p2, 5), is(not(equalTo(ValidationResult.SUCCESS))));
    }
}
