package org.zetool.common.algorithm.template;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ParameterAssignmentMap {

    private final Map<ParameterTemplate<?>, ParameterAssignment<?>> values = new HashMap<>();

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
     * Checks whether this parameter has a potential next value (which might not be valid).
     *
     * @param template
     * @param <T>
     * @return <code>true</code> if a candidate for a next value exists, <code>false</code> otherwise.
     */
    //@Override
    public <T> boolean hasNextValue(ParameterTemplate<T> template) {
        return values.get(template).hasNextValue();
    }

    /**
     * Returns the number of values this parameter can take. Notice that this does only count the number of potential
     * values it can take, not all of them might be valid with regard to the whole parameter set. If the values are not
     * given by a <code>Collection</code> or <code>Sequence</code> all values are iterated to count the number of
     * values.
     *
     * @param template
     * @param <T>
     * @return the number of values this parameter can take.
     */
    public <T> int numberOfValues(ParameterTemplate<T> template) {
        return values.get(template).numberOfValues();
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
        ParameterAssignment<T> a = (ParameterAssignment<T>) (values.get(template));
        return a.getValue();
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
        return template.validate(value);
    }

}
