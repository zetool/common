package org.zetool.common.datastructure.parameter;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.zetool.common.algorithm.template.ParameterTemplate;
import org.zetool.common.algorithm.template.ParameterTemplateSet;

/**
 * A collection of parameters and corresponding values.
 */
public class ParameterSet {
    private static final ParameterSet EMPTY_PARAMETER_SET = new ParameterSet();

    /**
     * Stores the listeners of this set.
     */
    private final Set<Parameter<?>> parameters = new LinkedHashSet<>();

    /**
     * Private constructor for the empty parameter set.
     */
    private ParameterSet() {
        // Empty
    }
    
    /**
     * Creates a parameter set containing the parameters defined in a {@link ParameterTemplateSet}.
     * For each {@link ParameterTemplate} in the template set, this parameter set will contain the
     * corresponding parameter with the value of the parameter in the template set.
     * 
     * @param parameterTemplates a set of parameter templates with corresponding values
     */
    public ParameterSet(ParameterTemplateSet parameterTemplates) {
        for (ParameterTemplate t : parameterTemplates) {
            this.parameters.add(t.getParameter(parameterTemplates.getValue(t)));
        }
    }
    
    /**
     * Returns an empty parameter set.
     * 
     * @return an empty parameter set.
     */
    public static ParameterSet emptyParameterSet() {
        return EMPTY_PARAMETER_SET;
    }

    public Iterator<Parameter<?>> iterator() {
        return parameters.iterator();
    }

    public int size() {
        return parameters.size();
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }
}
