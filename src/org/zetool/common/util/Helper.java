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
package org.zetool.common.util;

import org.zetool.common.util.units.UnitScale;
import java.util.Iterator;

/**
 * Some helper methods that are needed every now and then.
 *
 * @author jan-Philipp Kappmeier
 */
public final class Helper {
  private Helper() {
  }

  /**
   * Pauses the program for fileSizes specified time
   *
   * @param wait the pause time in milliseconds
   */
  public static void pause( long wait ) {
    try {
      Thread.sleep( wait );
    } catch( InterruptedException ignore ) {
    }
  }

  /**
   * Checks weather a value is between two other values, or not. {@code true} is returned if the value is directly on
   * the lower or upper bound.
   *
   * @param value the value
   * @param lower the lower bound
   * @param upper the upper bound
   * @return {@code false} if the value is outside of the bounds, {@code true} if it is inside
   */
  public static boolean isBetween( char value, char lower, char upper ) {
    return value >= lower && value <= upper;
  }

  public static <E extends UnitScale<E>> E getNextBetter( E unit, double value ) {
    if( value < 1 && unit.getSmaller() != null ) {
      return unit.getSmaller();
    } else if( value >= unit.getRange() ) {
      return unit.getLarger();
    } else {
      return unit;
    }
  }

  /**
   * <p>Converts a value in a given unit to a value in a different unit, if it improves readability. Conversion is
   * performed if the value is smaller than 1 in the current unit (and the unit allows for another smaller unit) or if
   * the value is too high and the unit allows for a larger unit. For SI units these ranges are typically from 1 to
   * 1000, binary units range to 1024.</p>
   * @param <E> the unit type
   * @param unit the unit
   * @param value the value
   * @return the value in a probably different unit, if possible between 1 and the range of the unit.
   */
  public static <E extends UnitScale<E>> double getNextBetterValue( final E unit, final double value ) {
    if( value < 1 && unit.getSmaller() != null ) {
      return value * unit.getSmaller().getRange();
    } else if( value >= unit.getRange() ) {
      return value / unit.getRange();
    } else {
      return value;
    }
  }

  /**
   * Adapts an {@link Iterator} to an {@link Iterable} for use in enhanced for loops. If {@link Iterable#iterator()} is
   * invoked more than once, an {@link IllegalStateException} is thrown.
   *
   * @param <T> the iterator type
   * @param iterator the iterator
   * @throws IllegalStateException if {@link Iterable#iterator() } is invoked more than once.
   * @return an iterable of an iterator
   */
  public static <T> Iterable<T> in( final Iterator<T> iterator ) throws IllegalStateException {
    assert iterator != null;
    class SingleUseIterable implements Iterable<T> {
      private boolean used = false;

      @Override
      public Iterator<T> iterator() {
        if( used ) {
          throw new IllegalStateException( "SingleUseIterable already invoked" );
        }
        used = true;
        return iterator;
      }
    }
    return new SingleUseIterable();
  }
}
