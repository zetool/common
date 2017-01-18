package org.zetool.common.algorithm.template;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A parameter assignment map is a mapping of values to parameters. The parameters are initially specified by a
 * {@link ParameterTemplateSet} and each of the parameters within the set is assigned its default value.
 * 
 * @author Jan-Philipp Kappmeier
 */
public class ParameterAssignmentMap implements Iterable<ParameterTemplate<?>> {

    private final Map<ParameterTemplate<?>, ParameterAssignment<?>> values = new HashMap<>();

    public int size() {
        return values.size();
    }
    
    public ParameterAssignmentMap(ParameterTemplateSet parameterTemplateSet) {
        for (ParameterTemplate<?> template : parameterTemplateSet) {
            ParameterAssignment<?> a = getAssignment(template);
            values.put(template, a);
        }
    }

    private static <T> ParameterAssignment<T> getAssignment(ParameterTemplate<T> template) {
        return new ParameterAssignment<>(template);
    }

    /**
     * Sets this parameter to the first valid value and returns <code>true</code> if it finds one and <code>false</code>
     * if it finds none. This also updates the internal iterator to this position. If the parameter has only one value,
     * nothing happens, which is counted as a success by the method.
     *
     * @param <T>
     * @param template
     * @return a <code>ValidationResult</code> specifying whether the operation was a success or a failure.
     */
    public <T> ValidationResult setToFirstValue(ParameterTemplate<T> template) {
        return values.get(template).setToFirstValue();
    }

    /**
     * If this parameter has a sequence of values, this sets the current value to the next value in the sequence which
     * is valid and returns <code>true</code> if a valid next value was found. Otherwise, <code>false</code> is
     * returned. If the parameter just has one value, the current value remains the same and <code>false</code> is
     * returned.
     *
     * @param <T>
     * @param template
     * @return a <code>ValidationResult</code> specifying whether this a success or a failure.
     */
    public <T> ValidationResult setToNextValue(ParameterTemplate<T> template) {
        return values.get(template).setToNextValue();
    }

    /**
     * Returns the current value of this parameter.
     *
     * @param <T>
     * @param template
     * @return the current value of this parameter.
     */
    public <T> T getValue(ParameterTemplate<T> template) {
        if (!contains(template)) {
            throw new IllegalArgumentException(String.format("Element %s not in list", template));
        }
        return ((ParameterAssignment<T>) values.get(template)).getValue();
    }

    /**
     * Changes the value of this parameter to the specified one. Before the value is changed, it is first checked by {@link #validate(Object)
     * } and then by the parameter set, if this parameter belongs to one. If both checks are passed, the value is
     * changed and the parent parameter set is notified.
     *
     * @param <T>
     * @param template
     * @param value the new value of this parameter.
     * @return a {@link ValidationResult} specifying whether the operation was a success or a failure.
     */
    public <T> ValidationResult setValue(ParameterTemplate<T> template, T value) {
        ParameterAssignment<T> a = (ParameterAssignment<T>) (values.get(template));
        return a.setValue(value);
    }

    public <T> ValidationResult isChangeValid(ParameterTemplate<T> template, T value) {
        return template.isValid(value);
    }

    @Override
    public Iterator<ParameterTemplate<?>> iterator() {
        return values.keySet().iterator();
    }

    public <T> boolean contains(ParameterTemplate<T> template) {
        return values.keySet().contains(template);
    }

}
