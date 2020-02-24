package org.zetool.common.datastructure.parameter;

/**
 * A {@code Parameter} consists of the name (for the parameter) and a value.
 * @param <T>
 */
public class Parameter<T> {

    /** The name of this parameter. */
    private final String name;
    /** The current value of this parameter. */
    private final T value;

    public Parameter(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }
}
