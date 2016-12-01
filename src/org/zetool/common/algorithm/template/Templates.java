package org.zetool.common.algorithm.template;

import java.util.Collections;
import java.util.Iterator;

/**
 * Some methods useful to work with parameters.
 * 
 * @author Jan-Philipp Kappmeier
 */
public class Templates {

    /**
     * Returns an empty parameter set. It returns an empty iterator, contains no {@link ParameterTemplate}s and all
     * methods to change or access a {@link ParameterTemplate} throw an {@link IllegalArgumentException}.
     * 
     * @return an empty parameter template set
     */
    public static ParameterTemplateSet emptyParameters() {

        return EmptyParameterTemplateSet.EMPTY_PARAMETER_SET;
    }

    private static class EmptyParameterTemplateSet implements ParameterTemplateSet {

        static final EmptyParameterTemplateSet EMPTY_PARAMETER_SET
                = new EmptyParameterTemplateSet();

        @Override
        public <T> T getValue(ParameterTemplate<T> template) {
            throw new IllegalArgumentException();
        }

        @Override
        public <T> DefaultParameterTemplate.ValidationResult isChangeValid(ParameterTemplate<T> template, T value) {
            throw new IllegalArgumentException();
        }

        @Override
        public <T> DefaultParameterTemplate.ValidationResult update(ParameterTemplate<T> template, T value) {
            throw new IllegalArgumentException();
        }

        @Override
        public Iterator<ParameterTemplate<?>> iterator() {
            return Collections.emptyIterator();
        }

        @Override
        public <T> boolean contains(ParameterTemplate<T> template) {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

    };

}
