/*
 * BooleanParameterTemplate.java
 * 
 */
package org.zetool.common.algorithm.template;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A class representing algorithmic parameters that take Boolean values.
 *
 * @author Martin Groß
 */
public class BooleanParameterTemplate extends DefaultParameterTemplate<Boolean> {
    private static final List<Boolean> ALL_VALUES = Arrays.asList(Boolean.TRUE, Boolean.FALSE);

    /**
     * Creates a new boolean parameter with the given name and description. The default value of the boolean parameter
     * is as given, its possible values are {@code true} and {@code false}.
     *
     * @param name the name of this parameter.
     * @param description the description of this parameter.
     * @param value the default value for this parameter.
     */
    public BooleanParameterTemplate(String name, String description, Boolean value) {
        this(name, description, ALL_VALUES, value);
    }

    /**
     * Special private constructor for the constant parameters.
     * 
     * @param name the name of this parameter.
     * @param description the description of this parameter.
     * @param value the default value for this parameter.
     */
    private BooleanParameterTemplate(String name, String desc, Iterable<Boolean> values, Boolean value) {
        super(name, desc, Boolean.TYPE, values, value);
    }

    /**
     * Creates a constant parameter with the given value.
     * 
     * @param name the name of this parameter
     * @param description the description of this parameter
     * @param value the constant value for this parameter
     * @return the new constant boolean prameter template
     */
    public static BooleanParameterTemplate constant(String name, String description, Boolean value) {
        return new BooleanParameterTemplate(name, description, Collections.singleton(value), value);
    }
}
