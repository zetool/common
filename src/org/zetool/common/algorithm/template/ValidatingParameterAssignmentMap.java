package org.zetool.common.algorithm.template;

/**
 *
 */
public abstract class ValidatingParameterAssignmentMap extends ParameterAssignmentMap {

    protected ValidatingParameterAssignmentMap(ParameterTemplateSet parameterTemplates) {
        super(parameterTemplates);
    }

    @Override
    public <T> ValidationResult isChangeValid(ParameterTemplate<T> template, T value) {
        ValidationResult result = super.isChangeValid(template, value);
        if (result != ValidationResult.SUCCESS) {
            return result;
        }
        
        // It is definitley valid to set the parameter to the specified value. Now check if it is compatible with
        // the other parameters
        return validate(template, value);
    }

    /**
     * Custom validation rule.
     * 
     * @return 
     */
    abstract <T> ValidationResult validate(ParameterTemplate<T> template, T value);
}
