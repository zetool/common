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
      // ignore
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
