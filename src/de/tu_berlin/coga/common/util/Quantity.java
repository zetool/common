/**
 * Quantity.java
 * Created: 04.04.2014, 10:08:38
 */
package de.tu_berlin.coga.common.util;

import de.tu_berlin.coga.common.util.units.UnitScale;

/**
 * A {@code Quantity} represents a physical quantity containing the actual value
 * of the quanitity and its unit. This class is immbla
 * @author Jan-Philipp Kappmeier
 * @param <E>
 */
public class Quantity<E extends UnitScale<E>> implements Comparable<Quantity<E>> {
	private final double value;
	private final E unit;

	public Quantity( double value, E unit ) {
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public E getUnit() {
		return unit;
	}

  @Override
  public String toString() {
    return Formatter.formatUnit( value, unit );
  }

  @Override
  public int compareTo( Quantity<E> o ) {
    // TODO: bring to same unit
    return (int)(o.getValue() - getValue());
  }
}
