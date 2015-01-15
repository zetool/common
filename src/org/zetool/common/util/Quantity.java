
package org.zetool.common.util;

import org.zetool.common.util.units.UnitScale;

/**
 * A {@code Quantity} represents a physical quantity containing the actual value
 * of the quanitity and its unit. This class is immbla
 * @author Jan-Philipp Kappmeier
 * @param <E> the unit scale of the quantity, e.g. time or length.
 */
public class Quantity<E extends UnitScale<E>> implements Comparable<Quantity<E>> {
	private final double value;
  private final long integralValue;
  private final boolean isIntegral;
	private final E unit;

  /**
   * Initializes the quantity with an arbitrary value.
   * @param value the value
   * @param unit the unit of the value
   */
	public Quantity( double value, E unit ) {
		this.value = value;
    isIntegral = false;
		this.unit = unit;
    integralValue = (long)value;
	}

  /**
   * Initializes the quantity with an integral value.
   * @param value the value
   * @param unit the unit of the value
   */
  public Quantity( long value, E unit ) {
    this.value = value;
    this.integralValue = value;
    this.unit = unit;
    isIntegral = true;
  }

	public double getValue() {
		return value;
	}

	public E getUnit() {
		return unit;
	}

  public boolean isIntegral() {
    return isIntegral;
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

  /**
   * Returns a new quantity that is of value of the sum of this quantity and the parameter.
   * If both quantities are integral, the resulting quantity is integral.
   * @param other the parameter
   * @return a quantity with the added value
   */
  public Quantity<E> add( Quantity<E> other ) {
    if( other.unit.equals( this.unit ) ) {
      if( this.isIntegral && other.isIntegral ) {
        return new Quantity<>( this.integralValue + other.integralValue, this.unit );
      } else {
        return new Quantity<>( this.value + other.integralValue, this.unit );
      }
    } else {
      throw new UnsupportedOperationException( "Adding different time units is not yet supported!" );
    }
  }

  /**
   * Returns a new quantity whose value is reduced by the parameters value.
   * If both quantities are integral, the resulting quantity is integral.
   * @param other the other quantity, whose amout is subtracted.
   * @return a quantity with the subtracted value
   */
  public Quantity<E> sub( Quantity<E> other ) {
    if( other.unit.equals( this.unit ) ) {
      if( this.isIntegral && other.isIntegral ) {
        return new Quantity<>( this.integralValue - other.integralValue, this.unit );
      } else {
        return new Quantity<>( this.value - other.integralValue, this.unit );
      }
    } else {
      throw new UnsupportedOperationException( "Adding different time units is not yet supported!" );
    }
  }
}
