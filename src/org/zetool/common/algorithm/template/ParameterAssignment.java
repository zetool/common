package org.zetool.common.algorithm.template;

import java.util.Iterator;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ParameterAssignment<T> {

    final ParameterTemplate<T> parameterTemplate;
    T value;
    /**
     * An iterator to the possible values of this parameter.
     */
    private Iterator<T> iterator;

    public ParameterAssignment(ParameterTemplate<T> parameterTemplate) {
        this.parameterTemplate = parameterTemplate;
    }

    /**
     * Checks whether this parameter has a potential next value (which might not be valid).
     *
     * @return <code>true</code> if a candidate for a next value exists, <code>false</code> otherwise.
     */
    //@Override
    public boolean hasNextValue() {
        return iterator != null && iterator.hasNext();
    }

    /**
     * Returns the number of values this parameter can take. Notice that this does only count the number of potential
     * values it can take, not all of them might be valid with regard to the whole parameter set. If the values are not
     * given by a <code>Collection</code> or <code>Sequence</code> all values are iterated to count the number of
     * values.
     *
     * @return the number of values this parameter can take.
     */
    public int numberOfValues() {
        return parameterTemplate.numberOfValues();
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

        Iterator<T> newIterator = parameterTemplate.getValues().iterator();
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
     * If this parameter has a sequence of values, this sets the current value to the next value in the sequence which
     * is valid and returns <code>true</code> if a valid next value was found. Otherwise, <code>false</code> is
     * returned. If the parameter just has one value, the current value remains the same and <code>false</code> is
     * returned.
     *
     * @return a <code>ValidationResult</code> specifying whether this a success or a failure.
     */
    public ValidationResult setToNextValue() {
        if (iterator == null) {
            return new ValidationResult(false, "This parameter does not have "
                    + "multiple values.");
        } else if (!iterator.hasNext()) {
            return new ValidationResult(false, "This parameter has reached it "
                    + "last possible value.");
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
     * Returns the current value of this parameter.
     *
     * @return the current value of this parameter.
     */
    public T getValue() {
        return value;
    }

    /**
     * Changes the value of this parameter to the specified one. Before the value is changed, it is first checked by {@link #validate(Object)
     * } and then by the parameter set, if this parameter belongs to one. If both checks are passed, the value is
     * changed and the parent parameter set is notified.
     *
     * @param value the new value of this parameter.
     * @return a {@link ValidationResult} specifying whether the operation was a success or a failure.
     */
    public ValidationResult setValue(T value) {
        if (value != this.value) {
            ValidationResult result = parameterTemplate.validate(value);
            if (result.isSuccessful()) {
                this.value = value;
            }
            return result;
        }
        return ValidationResult.SUCCESS;
    }

}
