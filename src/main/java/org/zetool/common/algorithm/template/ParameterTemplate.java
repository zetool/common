package org.zetool.common.algorithm.template;

import java.util.Iterator;
import org.zetool.common.datastructure.parameter.Parameter;

/**
 *
 * @param <T>
 * @author Jan-Philipp Kappmeier
 */
public interface ParameterTemplate<T> extends Iterable<T> {

    /**
     * Returns an {@link Iterator} over all the values that this parameter can take.
     *
     * @return an {@link Iterator} over all the values that this parameter can take
     */
    @Override
    public Iterator<T> iterator();

    /**
     * Returns the description of this parameter.
     *
     * @return the description of this parameter.
     */
    String getDescription();
    
    /**
     * Returns the type of the parameter values.
     * 
     * @return the type of the parameter values
     */
    Class<T> getType();

    /**
     * Returns the name of this parameter.
     *
     * @return the name of this parameter.
     */
    String getName();

    /**
     * Returns the actual parameter for this template with the given value.
     * 
     * @param value the value that the parameter is set to
     * @return an acutal parameter for this template with the given value
     */
    Parameter<T> getParameter(T value);

    /**
     * Returns the default value for the parameter.
     * @return 
     */
    T getDefault();

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
     * Returns whether it is valid to set a specific value for the parameter.
     * @param value the value
     * @return if the value is allowed
     */
    ValidationResult isValid(T value);
}
