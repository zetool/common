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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.zetool.common.util;

import java.util.Iterator;

/**
 * Some helper methods that are needed every now and then.
 *
 * @author jan-Philipp Kappmeier
 */
public final class Helper {

    private static final String MESSAGE_OUT_OF_RANGE = "Value %s out of range [%s,%s].";

    /** Private utility class constructor. */
    private Helper() {
        // No code.
    }

    /**
     * Pauses the program for fileSizes specified time
     *
     * @param wait the pause time in milliseconds
     */
    public static void pause(long wait) {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException ignore) {
            // ignore
        }
    }

    /**
     * Checks weather a value is between two other values, or not. {@literal true} is returned if the value is directly
     * on the lower or upper bound.
     *
     * @param value the value
     * @param lower the lower bound
     * @param upper the upper bound
     * @return {@code false} if the value is outside of the bounds, {@code true} if it is inside
     */
    public static boolean isBetween(char value, char lower, char upper) {
        return value >= lower && value <= upper;
    }
    
    public static int requireInRange(int min, int max, int value) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(String.format(MESSAGE_OUT_OF_RANGE, value, min, max));
        }
        return value;
    }

    public static long requireInRange(long min, long max, long value) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(String.format(MESSAGE_OUT_OF_RANGE, value, min, max));
        }
        return value;
    }
    
    public static double requireInRange(double min, double max, double value) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(String.format(MESSAGE_OUT_OF_RANGE, value, min, max));
        }
        return value;
    }
    
    public static int requireNonNegative(int value) {
        if( value < 0) {
            throw new IllegalArgumentException("Value " + value + " is negative.");
        }
        return value;
    }
    
    public static double requireNonNegative(double value) {
        if( value < 0) {
            throw new IllegalArgumentException("Value " + value + " is negative.");
        }
        return value;
    }

    /**
     * Adapts an {@link Iterator} to an {@link Iterable} for use in enhanced for loops. If {@link Iterable#iterator()}
     * is invoked more than once, an {@link IllegalStateException} is thrown. The submitted {@link Iterator} is
     * completely iterated afterwards and its {@link Iterator#hasNext() } will return {@literal false}.
     *
     * @param <T> the iterator type
     * @param iterator the iterator
     * @throws IllegalStateException if {@link Iterable#iterator() } is invoked more than once.
     * @return an iterable of an iterator
     */
    public static <T> Iterable<T> in(final Iterator<T> iterator) throws IllegalStateException {
        assert iterator != null;
        class SingleUseIterable implements Iterable<T> {

            private boolean used = false;

            @Override
            public Iterator<T> iterator() {
                if (used) {
                    throw new IllegalStateException("SingleUseIterable already invoked");
                }
                used = true;
                return iterator;
            }
        }
        return new SingleUseIterable();
    }
}
