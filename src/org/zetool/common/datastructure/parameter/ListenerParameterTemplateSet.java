package org.zetool.common.datastructure.parameter;

import java.util.HashSet;
import java.util.Iterator;
import org.zetool.common.algorithm.template.DefaultParameterTemplate.ValidationResult;
import org.zetool.common.algorithm.template.ParameterTemplate;
import org.zetool.common.algorithm.template.ParameterTemplateSet;

/**
 *
 */
public class ListenerParameterTemplateSet implements ParameterTemplateSet {

    /**
     * Stores the listeners of this set.
     */
    private HashSet<ParameterChangedListener> listeners;

    private ParameterTemplateSet pts;

    @Override
    public <T> ValidationResult update(ParameterTemplate<T> template, T value) {
        T oldValue = getValue(template);
        ValidationResult result = pts.update(template, value);
        if (result == ValidationResult.SUCCESS) {
            fireParameterChanged(template, oldValue, value);
        }
        return result;
    }

    /**
     * Informs all listeners that a parameter in this set has been changed by calling the method described in their
     * interface.
     *
     * @param <T> the type of the parameter's value.
     * @param parameter the parameter that was changed.
     * @param oldValue the old value of the parameter.
     * @param newValue the new value of the parameter.
     */
    protected <T> void fireParameterChanged(ParameterTemplate<T> parameter, T oldValue, T newValue) {
        for (ParameterChangedListener listener : listeners) {
            listener.parameterChanged(parameter, oldValue, newValue);
        }
    }

    /**
     * Adds a new parameter changed listener that will receive notification when a parameter in this set changes.
     *
     * @param listener the listener to be added.
     */
    public void addParameterChangedListener(ParameterChangedListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes the specified listener.
     *
     * @param listener the listener to be removed.
     */
    public void removeParameterChangedListener(ParameterChangedListener listener) {
        listeners.remove(listener);
    }

    @Override
    public <T> T getValue(ParameterTemplate<T> template) {
        return pts.getValue(template);
    }

    @Override
    public <T> ValidationResult isChangeValid(ParameterTemplate<T> template, T value) {
        return pts.isChangeValid(template, value);
    }

    @Override
    public Iterator<ParameterTemplate<?>> iterator() {
        return pts.iterator();
    }

    @Override
    public <T> boolean contains(ParameterTemplate<T> template) {
        return pts.contains(template);
    }

    @Override
    public boolean isEmpty() {
        return pts.isEmpty();
    }
    
}
