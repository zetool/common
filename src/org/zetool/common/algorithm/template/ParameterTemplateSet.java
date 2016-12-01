package org.zetool.common.algorithm.template;

import org.zetool.common.algorithm.template.DefaultParameterTemplate.ValidationResult;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public interface ParameterTemplateSet extends Iterable<ParameterTemplate<?>> {

    /**
     * @param <T>
     * @param template
     * @throws IllegalArgumentException if template is not contained in the set
     * @return 
     */
    <T> T getValue(ParameterTemplate<T> template);

    /**
     * @param <T>
     * @param template
     * @param value
     * @throws IllegalArgumentException if template is not contained in the set
     * @return 
     */
    <T> ValidationResult isChangeValid(ParameterTemplate<T> template, T value);

    /**
     * 
     * @param <T>
     * @param template
     * @param value
     * @throws IllegalArgumentException if template is not contained in the set
     * @return 
     */
    <T> ValidationResult update(ParameterTemplate<T> template, T value);

    /**
     * Checks whether the template is contained in the set.
     * 
     * @param <T> 
     * @param template 
     * @return whether the template is contained in the set
     */
    <T> boolean contains(ParameterTemplate<T> template);
    
    /**
     * Returns whether the set is empty.
     * 
     * @return whether the set is empty
     */
    boolean isEmpty();
}
