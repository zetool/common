package org.zetool.common.datastructure.parameter;

import org.zetool.common.algorithm.template.DefaultParameterTemplate;
import org.zetool.common.algorithm.template.ParameterTemplateSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 */
public class ParameterSet {
    /**
     * Stores the listeners of this set.
     */
    private Set<Parameter<?>> parameters = new LinkedHashSet<>();

    public ParameterSet(ParameterTemplateSet parameterTemplates) {
        for (DefaultParameterTemplate t : parameterTemplates) {
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
