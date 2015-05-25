/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.zetool.common.util.units;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public final class Conversion {

  /**
   * Private constructor for utility class.
   */
  private Conversion() {
  }

  /**
   * Computes the correct value and fitting unit for a given pair of value and time unit. The
   * result is stored as a {@link UnitScale}.
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
