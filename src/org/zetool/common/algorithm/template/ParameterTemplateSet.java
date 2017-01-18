package org.zetool.common.algorithm.template;

/**
 * A parameter template set collects several {@link ParameterTemplate}s together.
 * 
 * @author Jan-Philipp Kappmeier
 */
public interface ParameterTemplateSet extends Iterable<ParameterTemplate<?>> {

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
    
    /**
     * Verifies that a given assignment for all the parameters in the template is valid. It is possible for a parameter
     * template set, that the values for each parameter are valid, but the combination of two parameters is not valid.
     * 
     * An example are two double parameters {@literal x} and {@literal y} with the allowed range {@literal [0,1]} and
     * the additional requirement {@literal x² + y² &lt; 1}, i.e. the parameters represent a point in the unit circle.
     * 
     * @param map the parameter assignment map
     * @return the result of the validation
     */
    ValidationResult isValid(ParameterAssignmentMap map);
}
