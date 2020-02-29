package org.zetool.common.datastructure.parameter;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.zetool.common.algorithm.template.ParameterAssignmentMap;
import org.zetool.common.algorithm.template.ParameterTemplate;
import org.zetool.common.algorithm.template.ParameterTemplateSet;

/**
 * A collection of parameters and corresponding values.
 */
public class ParameterSet implements Iterable<Parameter<?>> {
    private static final ParameterSet EMPTY_PARAMETER_SET = new ParameterSet();

    /** Stores the listeners of this set. */
    private final Set<Parameter<?>> parameters = new LinkedHashSet<>();

    /** Private constructor for the empty parameter set. */
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
    public ParameterSet(ParameterAssignmentMap parameterTemplates) {
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

    @Override
    public Iterator<Parameter<?>> iterator() {
        return parameters.iterator();
    }

    /**
     * Returns the size of the {@code Parameterset}. The size is the number of {@link Parameter}s contained in the set.
     * @return the number of parameters in the set
     */
    public int size() {
        return parameters.size();
    }

    /**
     * Returns whether the {@code ParameterSet} is empty. The set is empty when it does not contain any
     * {@link Parameter}.
     * @return whether the {@code ParameterSet} is empty
     */
    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    /**
     * Returns the {@link Stream} over all parameters.
     * 
     * @return a sequential stream over the parameters in the set
     */
    public Stream<Parameter<?>> stream() {
        return parameters.stream();
    }
}
