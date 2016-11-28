package org.zetool.common.datastructure.parameter;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.zetool.common.algorithm.template.ParameterTemplate;
import org.zetool.common.algorithm.template.ParameterTemplateSet;

/**
 *
 */
public class ParameterSet {
    /**
     * Stores the listeners of this set.
     */
    private final Set<Parameter<?>> parameters = new LinkedHashSet<>();

    public ParameterSet(ParameterTemplateSet parameterTemplates) {
        for (ParameterTemplate t : parameterTemplates) {
            this.parameters.add(t.getParameter(parameterTemplates.getValue(t)));
        }
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
