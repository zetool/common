package org.zetool.common.algorithm.template;

import java.util.Set;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class NonValidatingParameterTemplateSet extends AbstractParameterTemplateSet {

    public NonValidatingParameterTemplateSet() {
    }

    public NonValidatingParameterTemplateSet(Set<DefaultParameterTemplate<?>> parameterTemplates) {
        super(parameterTemplates);
    }
    
    @Override
    public <T> DefaultParameterTemplate.ValidationResult update(DefaultParameterTemplate<T> template, T value) {
        DefaultParameterTemplate.ValidationResult result = isChangeValid(template, value);
        if (result == DefaultParameterTemplate.ValidationResult.SUCCESS) {
            super.updateValue(template, value);
        }
        return result;
    }

}
