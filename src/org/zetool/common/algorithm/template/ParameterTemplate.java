package org.zetool.common.algorithm.template;

import org.zetool.common.algorithm.template.DefaultParameterTemplate.ValidationResult;
import org.zetool.common.datastructure.parameter.Parameter;

/**
 *
 * @param <T>
 * @author Jan-Philipp Kappmeier
 */
public interface ParameterTemplate<T> {

    /**
     * Returns the description of this parameter.
     *
     * @return the description of this parameter.
     */
    String getDescription();

    /**
     * Returns the name of this parameter.
     *
     * @return the name of this parameter.
     */
    String getName();

    Parameter<T> getParameter(T value);

    /**
     * Returns the <code>Iterable</code> providing the iterator for the values that this parameter
     * can take.
     *
     * @return the <code>Iterable</code> providing the iterator for the values that this parameter
     * can take.
     */
    Iterable<T> getValues();

    /**
     * Checks whether this parameter has a potential next value (which might not be valid).
     *
     * @return <code>true</code> if a candidate for a next value exists, <code>false</code>
     * otherwise.
     */
    //boolean hasNextValue();
    /**
     * Returns the number of values this parameter can take. Notice that this does only count the
     * number of potential values it can take, not all of them might be valid with regard to the
     * whole parameter set. If the values are not given by a <code>Collection</code> or
     * <code>Sequence</code> all values are iterated to count the number of values.
     *
     * @return the number of values this parameter can take.
     */
    int numberOfValues();

    /**
     * Sets this parameter to the first valid value and returns <code>true</code> if it finds one
     * and <code>false</code> if it finds none. This also updates the internal iterator to this
     * position. If the parameter has only one value, nothing happens, which is counted as a success
     * by the method.
     *
     * @return a <code>ValidationResult</code> specifying whether the operation was a success or a
     * failure.
     */
    //T getFirstValue();
    /**
     * If this parameter has a sequence of values, this sets the current value to the next value in
     * the sequence which is valid and returns <code>true</code> if a valid next value was found.
     * Otherwise, <code>false</code> is returned. If the parameter just has one value, the current
     * value remains the same and <code>false</code> is returned.
     *
     * @return a <code>ValidationResult</code> specifying whether this a success or a failure.
     */
    //T getNextValue();
    //    /**
    //     * Returns the current value of this parameter.
    //     *
    //     * @return the current value of this parameter.
    //     */
    //    public T getValue() {
    //        return value;
    //    }
    ValidationResult validate(T value);

}
