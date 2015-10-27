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
package org.zetool.common.util.units;

import java.util.Objects;
import org.zetool.common.util.Formatter;

/**
 * A {@code Quantity} represents a physical quantity containing the actual value of the quanitity and its unit. This
 * class is immutable.
 *
 * @author Jan-Philipp Kappmeier
 * @param <E> the unit scale of the quantity, e.g. time or length.
 */
public class Quantity<E extends UnitScale<E>> implements Comparable<Quantity<E>> {

    /** The value of the unit. */
    private final double value;
    /** The integral value of the unit. */
    private final long integralValue;
    /** Stores whether the stored value can be assumed to be integral. */
    private final boolean isIntegral;
    /** The unit of the physical quantity. */
    private final E unit;

    /**
     * Initializes the quantity with an arbitrary value.
     *
     * @param value the value
     * @param unit the unit of the value
     */
    public Quantity(double value, E unit) {
        this.value = value;
        isIntegral = Double.doubleToRawLongBits(value - Math.rint(value)) == 0;
        integralValue = (long) Math.rint(value);
        this.unit = Objects.requireNonNull(unit);
    }

    /**
     * Initializes the quantity with an integral value.
     *
     * @param value the value
     * @param unit the unit of the value
     */
    public Quantity(long value, E unit) {
        this.value = value;
        this.integralValue = value;
        this.unit = Objects.requireNonNull(unit);
        isIntegral = true;
    }

    public double getValue() {
        return value;
    }

    public E getUnit() {
        return unit;
    }

    public long getIntegralValue() {
        if (!isIntegral) {
            throw new IllegalStateException("Not integral: " + value);
        }
        return integralValue;
    }

    public boolean isIntegral() {
        return isIntegral;
    }

    @Override
    public String toString() {
        return Formatter.formatUnit(value, unit);
    }

    @Override
    public int compareTo(Quantity<E> other) {
        if (other.unit.equals(this.unit)) {
            return (int) (other.getValue() - getValue());
        } else {
            throw new UnsupportedOperationException("Comparing different units is not yet supported!");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Quantity<?> other = (Quantity<?>) obj;
        if (isIntegral && other.isIntegral) {
            return this.integralValue == other.integralValue;
        } else {
            return Double.doubleToLongBits(this.value) == Double.doubleToLongBits(other.value);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.unit);
        return hash;
    }

    /**
     * Returns a new quantity that is of value of the sum of this quantity and the parameter. If both quantities are
     * integral, the resulting quantity is integral.
     *
     * @param other the parameter
     * @return a quantity with the added value
     */
    public Quantity<E> add(Quantity<E> other) {
        if (other.unit.equals(this.unit)) {
            if (this.isIntegral && other.isIntegral) {
                return new Quantity<>(this.integralValue + other.integralValue, this.unit);
            } else {
                return new Quantity<>(this.value + other.value, this.unit);
            }
        } else {
            throw new UnsupportedOperationException("Adding different time units is not yet supported!");
        }
    }

    /**
     * Returns a new quantity whose value is reduced by the parameters value. If both quantities are integral, the
     * resulting quantity is integral.
     *
     * @param other the other quantity, whose amout is subtracted.
     * @return a quantity with the subtracted value
     */
    public Quantity<E> sub(Quantity<E> other) {
        if (other.unit.equals(this.unit)) {
            if (this.isIntegral && other.isIntegral) {
                return new Quantity<>(this.integralValue - other.integralValue, this.unit);
            } else {
                return new Quantity<>(this.value - other.value, this.unit);
            }
        } else {
            throw new UnsupportedOperationException("Adding different units is not yet supported!");
        }
    }

}
