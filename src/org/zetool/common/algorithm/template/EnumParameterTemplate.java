/*
 * EnumParameterTemplate.java
 *
 */
package org.zetool.common.algorithm.template;

/**
 * A class representing algorithmic parameters that take a value from an enumeration.
 *
 * @author Martin Groß
 */
public class EnumParameterTemplate<T extends Enum> extends DefaultParameterTemplate<T> {

    /**
     * The enumeration type this parameter takes values from.
     */
    private Class<T> enumType;

    /**
     * Creates a new EnumParameter with the given name and description, that belongs to the specified parameter set and
     * has the given default value. Furthermore, the type of the enumeration is specified.
     *
     * @param parent the parameter set this parameter belongs to.
     * @param name the name of this parameter.
     * @param description the description of this parameter.
     * @param enumType the enumeration the allowed values are from.
     * @param value the default value for this parameter.
     */
    public EnumParameterTemplate(String name, String description, Class<T> enumType, T value) {
        super(name, description, value);
        this.enumType = enumType;
    }

    /**
     * Returns the enumeration type this parameter takes values from.
     *
     * @return the enumeration type this parameter takes values from.
     */
    public Class<T> getEnumType() {
        return enumType;
    }
}
