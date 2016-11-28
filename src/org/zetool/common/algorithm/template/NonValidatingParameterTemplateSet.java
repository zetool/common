package org.zetool.common.algorithm.template;

import java.util.Set;
import org.zetool.common.algorithm.template.DefaultParameterTemplate.ValidationResult;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class NonValidatingParameterTemplateSet extends AbstractParameterTemplateSet {

    public NonValidatingParameterTemplateSet() {
    }

    public NonValidatingParameterTemplateSet(Set<ParameterTemplate<?>> parameterTemplates) {
        super(parameterTemplates);
    }
    
    @Override
    public <T> ValidationResult update(ParameterTemplate<T> template, T value) {
        ValidationResult result = isChangeValid(template, value);
        if (result == ValidationResult.SUCCESS) {
            super.updateValue(template, value);
        }
        return result;
    }

}
