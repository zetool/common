
package org.zetool.common.util.units;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public final class Conversion {

  /** Private constructor for utility class. */
  private Conversion() { }

  /**
   * Computes the correct value and fitting time unit for a given pair of value and time unit. The result is stored as a
   * {@link UnitScale}.
   *
   * @param <U> the type of time unit that is returned
   * @param value the value of the number to be formatted
   * @param unit the unit of the number
   * @return the pair containing the transformed value and the fitting unit
   */
  public static <U extends UnitScale<U>> Quantity<U> unit( double value, U unit ) {
    U currentUnit = unit;
    double restValue = value;
    while( !currentUnit.isInRange( restValue ) ) {
      final double newValue = currentUnit.getBetterUnitValue( restValue );
      currentUnit = currentUnit.getBetterUnit( restValue );
      restValue = newValue;
    }
    return new Quantity<>( restValue, currentUnit );
  }
  
}
