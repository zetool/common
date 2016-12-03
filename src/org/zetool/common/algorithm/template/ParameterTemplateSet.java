package org.zetool.common.algorithm.template;

import org.zetool.common.algorithm.template.ValidationResult;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public interface ParameterTemplateSet extends Iterable<DefaultParameterTemplate<?>> {
    <T> T getValue(DefaultParameterTemplate<T> template);

    <T> ValidationResult isChangeValid(DefaultParameterTemplate<T> template, T value);

    <T> ValidationResult update(DefaultParameterTemplate<T> template, T value);

}
