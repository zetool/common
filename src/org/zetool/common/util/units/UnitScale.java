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
 * The {@code UnitScale} interface is upposed to provide conversion opportunities between different multiples of values
 * in a specific unit. It is supposed to be implemented by enums which may for example specify different multiples of
 * time or lengths.
 *
 * @param <T> the unit for which the scales are implemented (e. g. time).
 * @author Jan-Philipp Kappmeier
 */
public interface UnitScale<T> {

  /**
   * Returns the next smaller unit in a unit scale. For example, the next smaller unit to milliseconds in an SI time
   * scale are are microseconds. The values returned by {@link #getSmaller()} and {@link #getLarger() } are such that
   * {@code unit.getSmaller().getLarger()} is again {@literal unit} for .
   * 
   * @return the next smaller unit
   */
  public T getSmaller();

  /**
   * Returns the next larger unit in a unit scale. For example, the next larger unit to milliseconds in an SI time scale
   * are seconds. The values returned by {@link #getSmaller()} and {@link #getLarger() } are such that
   * {@code unit.getLarger().getSmaller()} is again {@literal unit}.
   * 
   * @return the next larger unit
   */
  public T getLarger();

  /**
   * Returns the symbol used for the time unit. For example {@literal s} for seconds.
   * 
   * @return the symbol for the time unit
   */
  public String getSymbol();

  /**
   * Returns a longer representation for the time unit. For example {@literal seconds}.
   *
   * @return a longer represetnation for the time unit
   */
  public String getName();

  /**
   * Returns the maximum value which this time unit can represent until it is better to switch to the next larger unit.
   * For time scale with successive units {@literal milliseconds} and {@literal seconds}, the range of milliseconds is
   * {@literal 1000} as {@literal 1000} milliseconds equal one second.
   *
   * @return the maximum value which this time unit can represent best
   */
  public double getRange();
  
  /**
   * Checks, if the value is good for the current unit, or if it can be scaled to fit better.
   *
   * @param value the value that is checked
   * @return {@code true} if there is no need to scale the value to fit a better unit, {@code false} otherwise
   */
  public boolean isInRange( double value );

  /**
   * Returns the next better value to represent the measure.
   *
   * @param value the current value (in the unit specified by the instance of the enumeration)
   * @return the transformed measure
   */
  public T getBetterUnit( double value );

  /**
   * Returns the next better unit to represent the measure.
   *
   * @param value the current value (in the unit specified by the instance of the enumeration)
   * @return the transformed unit
   */
  public double getBetterUnitValue( double value );
}
