package org.zetool.common.algorithm.template;

import java.util.Iterator;

/**
 * A current assignment to a parameter specified by a {@link ParameterTemplate}. Can store the current value which has
 * to be among the valid values for the {@code ParameterSet}. It is also possible to iterate through all possible values
 * of the {@code ParameterTemplate}, see {@link #setToFirstValue()} and {@link #setToNextValue()}.
 *
 * @author Jan-Philipp Kappmeier
 */
public class ParameterAssignment<T> {

    /**
     * The template for the parameter.
     */
    final ParameterTemplate<T> parameterTemplate;
    /**
     * The current value assigned to the parameter.
     */
    T value;
    /**
     * An iterator to the possible values of this parameter.
     */
    private Iterator<T> iterator;

    /**
     * Initiates the assignment for a {@code ParameterTemplate} by setting its value to the default value.
     *
     * @param parameterTemplate the parameter template
     */
    public ParameterAssignment(ParameterTemplate<T> parameterTemplate) {
        this.parameterTemplate = parameterTemplate;
        this.value = parameterTemplate.getDefault();
    }

    /**
     * Returns the current value of this parameter.
     *
     * @return the current value of this parameter.
     */
    public T getValue() {
        return value;
    }

    /**
     * Changes the value of this parameter to the specified one. Before the value is changed, it is first checked by
     * {@link ParameterTemplate#isValid(java.lang.Object) the parameter template} and then by the parameter set, if this
     * parameter belongs to one. If both checks are passed, the value is changed and the parent parameter set is
     * notified.
     *
     * @param value the new value of this parameter.
     * @return a {@link ValidationResult} specifying whether the operation was a success or a failure.
     */
    public ValidationResult setValue(T value) {
        if (value != this.value) {
            ValidationResult result = parameterTemplate.isValid(value);
            if (result.isSuccessful()) {
                this.value = value;
            }
            return result;
        }
        return ValidationResult.SUCCESS;
    }

    /**
     * Sets this parameter to the first valid value and returns <code>true</code> if it finds one and <code>false</code>
     * if it finds none. This also updates the internal iterator to this position. If the parameter has only one value,
     * nothing happens, which is counted as a success by the method.
     *
     * @return a <code>ValidationResult</code> specifying whether the operation was a success or a failure.
     */
    public ValidationResult setToFirstValue() {
        if (numberOfValues() == 1) {
            return ValidationResult.SUCCESS;
        }

        Iterator<T> newIterator = parameterTemplate.iterator();
        ValidationResult result = ValidationResult.FAILURE;
        while (newIterator.hasNext() && !result.isSuccessful()) {
            T newValue = newIterator.next();
            result = setValue(newValue);
        }
        if (result.isSuccessful()) {
            iterator = newIterator;
        }
        return result;
    }

    /**
     * Checks whether this parameter has a potential next value (which might not be valid).
     *
     * @return {@code true} if a candidate for a next value exists, {@code false} otherwise.
     */
    public boolean hasNextValue() {
        return iterator != null && iterator.hasNext();
    }

    /**
     * If this parameter has a sequence of values, this sets the current value to the next value in the sequence which
     * is valid and returns {@code true} if a valid next value was found. Otherwise, {@code false} is returned. If the
     * parameter just has one value, the current value remains the same and {@code false} is returned.
     *
     * @see #hasNextValue()
     * @return a {@code ValidationResult} specifying whether this a success or a failure.
     */
    public ValidationResult setToNextValue() {
        if (!iterator.hasNext()) {
            return new ValidationResult(false, "This parameter has reached its last possible value.");
        } else {
            ValidationResult result;
            do {
                T nextValue = iterator.next();
                result = setValue(nextValue);
            } while (!result.isSuccessful() && iterator.hasNext());
            return result;
        }
    }

    /**
     * Returns the number of values this parameter can take. 
     * 
     * @see ParameterTemplate#numberOfValues() 
     * @return the number of values this parameter can take.
     */
    public int numberOfValues() {
        return parameterTemplate.numberOfValues();
    }
}
