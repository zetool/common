package org.zetool.common.algorithm.template;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.zetool.common.algorithm.parameter.Sequence;
import org.zetool.common.datastructure.parameter.Parameter;

/**
 *
 * @param <T>
 */
public class DefaultParameterTemplate<T> implements ParameterTemplate<T> {

    /**
     * The description of this parameter.
     */
    private final String description;
    /**
     * The name of this parameter.
     */
    private final String name;
    /** The default value for this parameter type. */
    private final T defaultValue;
    /**
     * A sequence of values for this parameter.
     */
    private final Iterable<T> values;
    private final Class<T> type;

    /**
     * Creates a new Parameter with the given name and description, that belongs to the specified parameter set and has
     * the given default value.
     *
     *
     * @param name the name of this parameter.
     * @param desc the description of this parameter.
     * @param type
     * @param value the single value for this parameter.
     */
    public DefaultParameterTemplate(String name, String desc, Class<T> type, T value) {
        this.description = desc;
        this.name = name;
        values = Collections.singleton(value);
        defaultValue = value;
        this.type = type;
    }

    /**
     * Creates a new Parameter with the given name and description, that belongs to the specified parameter set and has
     * the given default value. The default value will be the first value of the values
     *
     * @param name the name of this parameter.
     * @param desc the description of this parameter.
     * @param type
     * @param values the values for this parameter
     */
    public DefaultParameterTemplate(String name, String desc, Class<T> type, Iterable<T> values) {
        this.description = desc;
        this.name = name;
        this.values = values;
        defaultValue = values.iterator().next();
        this.type = type;
    }

    /**
     * Creates a new Parameter with the given name and description, that belongs to the specified parameter set and has
     * the given default value.
     *
     * @param name the name of this parameter.
     * @param desc the description of this parameter.
     * @param type
     * @param values the values for this parameter
     * @param defaultValue the default value for this parameter
     */
    public DefaultParameterTemplate(String name, String desc, Class<T> type, Iterable<T> values, T defaultValue) {
        this.description = desc;
        this.name = name;
        this.values = values;
        this.defaultValue = defaultValue;
        this.type = type;
    }

    @Override
    public Parameter<T> getParameter(T value) {
        return new Parameter<>(name, value);
    }

    /**
     * Returns the description of this parameter.
     *
     * @return the description of this parameter.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Returns the name of this parameter.
     *
     * @return the name of this parameter.
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getDefault() {
        return defaultValue;
    }

    /**
     * Returns the number of values this parameter can take. Notice that this does only count the number of potential
     * values it can take, not all of them might be valid with regard to the whole parameter set. If the values are not
     * given by a <code>Collection</code> or <code>Sequence</code> all values are iterated to count the number of
     * values.
     *
     * @return the number of values this parameter can take.
     */
    @Override
    public int numberOfValues() {
        if (values == null) {
            return 1;
        } else if (values instanceof Collection) {
            return ((Collection) values).size();
        } else if (values instanceof Sequence) {
            return ((Sequence) values).size();
        } else {
            int count = 0;
            Iterator<T> countingIterator = values.iterator();
            while (countingIterator.hasNext()) {
                countingIterator.next();
                ++count;
            }
            return count;
        }
    }

    /**
     * This method should be overwritten by subclasses. The default implementation returns always true.
     *
     * @param value the value that is to be validated.
     * @return the result of the validation.
     */
    @Override
    public ValidationResult isValid(T value) {
        return ValidationResult.SUCCESS;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    /**
     * Returns an {@link Iterator} over all the values that this parameter can take.
     *
     * @return an {@link Iterator} over all the values that this parameter can take
     */
    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }

}
