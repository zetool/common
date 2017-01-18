package org.zetool.common.algorithm.template;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public class DefaultParameterTemplateSet implements ParameterTemplateSet {

    private final Set<ParameterTemplate<? extends Object>> parameterTemplates;

    protected DefaultParameterTemplateSet(Set<ParameterTemplate<?>> parameterTemplates) {
        this.parameterTemplates = parameterTemplates;
    }

    @Override
    public Iterator<ParameterTemplate<?>> iterator() {
        return parameterTemplates.iterator();
    }

    @Override
    public ValidationResult isValid(ParameterAssignmentMap map) {
        // Validates all parameters
        //return template.isValid(value);
        for (ParameterTemplate<? extends Object> t : parameterTemplates ) {
            ValidationResult singleValidate = validate(t, map.getValue(t));
            if (singleValidate == ValidationResult.FAILURE) {
                return ValidationResult.FAILURE;
            }
        }
        return ValidationResult.SUCCESS;
    }

    private <T> ValidationResult validate(ParameterTemplate<T> t, Object v) {
        if (t.getType().isInstance(v)) {
            T vCast = t.getType().cast(v);
            return t.isValid(vCast);
        }
        return ValidationResult.FAILURE;
    }

    @Override
    public <T> boolean contains(ParameterTemplate<T> template) {
        return parameterTemplates.contains(template);
    }

    @Override
    public boolean isEmpty() {
        return parameterTemplates.isEmpty();
    }

    public static class Builder {

        private final Set<ParameterTemplate<?>> sets = new HashSet<>();

        /**
         * A shortcut for adding new parameters to this set. Supports Strings, Integers,
         * Enumerations, Booleans, Doubles and Objects.
         *
         * @param name the name of the new parameter.
         * @param description the description of the new parameter.
         * @param value the default value of this parameter.
         * @param params additional parameters that the constructors of the specific parameter
         * classes support. See their description for more information.
         * @return the {@code Builder} instance
         */
        public <T> Builder withParameter(String name, String description, T value, Object... params) {
            Class<?> type = value.getClass();
            DefaultParameterTemplate<T> parameter;
            if (type.equals(String.class)) {
                if (params.length == 0 || !(params[0] instanceof String)) {
                    parameter = (DefaultParameterTemplate<T>) new StringParameterTemplate(name, description, (String) value);
                } else {
                    parameter = (DefaultParameterTemplate<T>) new StringParameterTemplate(name, description, (String) value, (String) params[0]);
                }
            } else if (type.equals(Integer.class)) {
                if (params.length != 2 || !(params[0] instanceof Integer) || !(params[1] instanceof Integer)) {
                    parameter = (DefaultParameterTemplate<T>) new IntegerParameterTemplate(name, description, (Integer) value);
                } else {
                    parameter = (DefaultParameterTemplate<T>) new IntegerParameterTemplate(name, description, (Integer) value, (Integer) params[0], (Integer) params[1]);
                }
            } else if (type.equals(Boolean.class)) {
                parameter = (DefaultParameterTemplate<T>) new BooleanParameterTemplate(name, description, (Boolean) value);
            } else if (type.equals(Double.class)) {
                if (params.length != 2 || !(params[0] instanceof Double) || !(params[1] instanceof Double)) {
                    parameter = (DefaultParameterTemplate<T>) new DoubleParameterTemplate(name, description, (Double) value);
                } else {
                    parameter = (DefaultParameterTemplate<T>) new DoubleParameterTemplate(name, description, (Double) value, (Double) params[0], (Double) params[1]);
                }
            } else if (Enum.class.isAssignableFrom(type)) {
                parameter = new EnumParameterTemplate(name, description, (Class<? extends Enum>) type, (Enum) value);
            } else {
                parameter = new DefaultParameterTemplate(name, description, type, value);
            }
            sets.add(parameter);
            return this;
        }

        public ParameterTemplateSet build() {
            return new DefaultParameterTemplateSet(sets);
        }
    }

}
