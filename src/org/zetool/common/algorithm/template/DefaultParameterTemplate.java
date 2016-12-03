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
     * An iterator to the possible values of this parameter.
     */
    private Iterator<T> iterator;
    /**
     * The name of this parameter.
     */
    private final String name;

    /**
     * A sequence of values for this parameter.
     */
    private Iterable<T> values;

    /**
     * Creates a new Parameter with the given name and description, that belongs to the specified parameter set and has
     * the given default value.
     *
     *
     * @param name the name of this parameter.
     * @param desc the description of this parameter.
     * @param value the single value for this parameter.
     */
    public DefaultParameterTemplate(String name, String desc, T value) {
        this.description = desc;
        this.name = name;
        iterator = null;
        values = Collections.singleton(value);
    }

    /**
     * Creates a new Parameter with the given name and description, that belongs to the specified parameter set and has
     * the given default value.
     *
     *
     * @param name the name of this parameter.
     * @param desc the description of this parameter.
     * @param values the values for this parameter
     */
    public DefaultParameterTemplate(String name, String desc, Sequence<T> values) {
        this.description = desc;
        this.name = name;
        iterator = null;
        this.values = values;
    }

    /**
     * Creates a new Parameter with the given name and description, that belongs to the specified parameter set and has
     * the given default value.
     *
     *
     * @param name the name of this parameter.
     * @param desc the description of this parameter.
     * @param values the values for this parameter
     */
    public DefaultParameterTemplate(String name, String desc, Collection<T> values) {
        this.description = desc;
        this.name = name;
        iterator = null;
        this.values = values;
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
     * Returns the <code>Iterable</code> providing the iterator for the values that this parameter can take.
     *
     * @return the <code>Iterable</code> providing the iterator for the values that this parameter can take.
     */
    @Override
    public Iterable<T> getValues() {
        return values;
    }

    /**
     * This method should be overwritten by subclasses. The default implementation returns always true.
     *
     * @param value the value that is to be validated.
     * @return the result of the validation.
     */
    public ValidationResult validate(T value) {
        return ValidationResult.SUCCESS;
    }

    /**
     * Changes the values that this parameter can take to the specified <code>Iterable</code>. The current value is then
     * set to the first valid value of this <code>Iterable</code>, provided that one exists. If it does not, nothing
     * happens and a validation failure is returned.
     *
     * @param values an <code>Iterable</code> providing the values that this parameter can take.
     * @return a <code>ValidationResult</code> specifying whether the operation was a success or a failure.
     */
    private ValidationResult setValues(Iterable<T> values) {
        return null;
//        Iterable<T> oldValues = values;
//        this.values = values;
//        //ValidationResult result = setToFirstValue();
//        if (!result.isSuccessful()) {
//            this.values = oldValues;
//        } else {
//        }
//        return result;
    }

}
