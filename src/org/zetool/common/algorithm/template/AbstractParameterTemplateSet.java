package org.zetool.common.algorithm.template;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.zetool.common.datastructure.parameter.Parameter;
import org.zetool.common.algorithm.template.DefaultParameterTemplate.ValidationResult;

/**
 *
 */
public abstract class AbstractParameterTemplateSet implements ParameterTemplateSet {
    private final Set<DefaultParameterTemplate<?>> parameterTemplates;
    private final Map<DefaultParameterTemplate, Parameter> values = new HashMap<>();

    /**
     * Creates a new ParameterSet.
     */
    public AbstractParameterTemplateSet() {
        parameterTemplates = Collections.emptySet();
    }

    protected AbstractParameterTemplateSet(Set<DefaultParameterTemplate<?>> parameterTemplates) {
        this.parameterTemplates = parameterTemplates;
    }
    
    @Override
    public <T> ValidationResult isChangeValid(DefaultParameterTemplate<T> template, T value) {
        return template.validate(value);
    }
    
    public <T> T getValue(DefaultParameterTemplate<T> template) {
        return (T) values.get(template);
    }
    
    protected <T> void updateValue(DefaultParameterTemplate<T> template, T value) {
        values.put(template, template.getParameter(value));
    }

    @Override
    public Iterator<DefaultParameterTemplate<?>> iterator() {
        return parameterTemplates.iterator();
    }

}
