
package org.zetool.common.util;

/**
 * Filters objects of a given type.
 * @param <E> the type of objects that is filtered
 * @author Jan-Philipp Kappmeier
 */
public interface Filter<E> {
  /**
   * Decides if an element should be accepted by the filter.
   * @param e the element
   * @return {@code true} if the element is accepted by the filter, {@code false} otherwise
   */
  boolean accept( E e );
}
