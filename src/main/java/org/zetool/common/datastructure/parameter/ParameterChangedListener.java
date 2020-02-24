/*
 * ParameterChangedListener.java
 * 
 */
package org.zetool.common.datastructure.parameter;

import org.zetool.common.algorithm.template.ParameterTemplate;

/**
 * An interface for classes that want to receive parameter changed events.
 *
 * @author Martin Groß
 */
public interface ParameterChangedListener {

    /**
     * This is called by parameter sets which the listener has subscribed to,
     * when the value of one of its parameters changes.
     * @param <T> the type of the value of the changing parameter.
     * @param template the parameter that has changed.
     * @param oldValue the old value of the parameter.
     * @param newValue the new value of the parameter.
     */
    <T> void parameterChanged(ParameterTemplate<T> template , T oldValue, T newValue);

}
