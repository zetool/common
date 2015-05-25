package org.zetool.common.datastructure;

/**
 * Represents two values that belong together. The values may be of different type.
 *
 * @param <U> the type of the first value
 * @param <V> the type of the second value
 * @author Jan-Philipp Kappmeier
 */
public interface Tuple<U, V> {

    public U getU();

    public V getV();
}
