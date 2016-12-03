/*
 * ParameterChangedListener.java
 * 
 */
package org.zetool.common.datastructure.parameter;

import org.zetool.common.algorithm.template.DefaultParameterTemplate;

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
     * @param parameter the parameter that has changed.
     * @param oldValue the old value of the parameter.
     * @param newValue the new value of the parameter.
     */
    <T> void parameterChanged(DefaultParameterTemplate<T> parameter, T oldValue, T newValue);

}
