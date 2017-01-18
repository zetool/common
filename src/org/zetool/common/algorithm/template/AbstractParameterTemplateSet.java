package org.zetool.common.algorithm.template;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.zetool.common.datastructure.parameter.Parameter;

/**
 *
 */
public abstract class AbstractParameterTemplateSet implements ParameterTemplateSet {

    private final Set<ParameterTemplate<?>> parameterTemplates;
    private final Map<ParameterTemplate, Parameter> values = new HashMap<>();

    /**
     * Creates a new ParameterSet.
     */
    public AbstractParameterTemplateSet() {
        parameterTemplates = Collections.emptySet();
    }

    protected AbstractParameterTemplateSet(Set<ParameterTemplate<?>> parameterTemplates) {
        this.parameterTemplates = parameterTemplates;
    }

    protected <T> void updateValue(ParameterTemplate<T> template, T value) {
        values.put(template, template.getParameter(value));
    }

    @Override
    public Iterator<ParameterTemplate<?>> iterator() {
        return parameterTemplates.iterator();
    }

    @Override
    public <T> boolean contains(ParameterTemplate<T> template) {
        return values.containsKey(template);
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

}
