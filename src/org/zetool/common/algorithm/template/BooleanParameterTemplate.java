/*
 * BooleanParameterTemplate.java
 * 
 */
package org.zetool.common.algorithm.template;

/**
 * A class representing algorithmic parameters that take Boolean values.
 *
 * @author Martin Groß
 */
public class BooleanParameterTemplate extends DefaultParameterTemplate<Boolean> {

    /**
     * Creates a new BooleanParameter with the given name and description, that belongs to the specified parameter set
     * and has the given default value.
     *
     * @param name the name of this parameter.
     * @param description the description of this parameter.
     * @param value the default value for this parameter.
     */
    public BooleanParameterTemplate(String name, String description, Boolean value) {
        super(name, description, value);
    }
}
