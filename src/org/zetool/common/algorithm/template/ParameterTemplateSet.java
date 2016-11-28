package org.zetool.common.algorithm.template;

import org.zetool.common.algorithm.template.DefaultParameterTemplate.ValidationResult;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public interface ParameterTemplateSet extends Iterable<ParameterTemplate<?>> {
    <T> T getValue(ParameterTemplate<T> temwplate);

    <T> ValidationResult isChangeValid(ParameterTemplate<T> template, T value);

    <T> ValidationResult update(ParameterTemplate<T> template, T value);

}
