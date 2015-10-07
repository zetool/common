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
package org.zetool.common.datastructure;

import java.util.Objects;

/**
 * A simple immutable {@link Tuple} implementation. Inherit for specialized use and custom
 * accessors.
 * @author Jan-Philipp Kappmeier
 * @param <U> the first data type
 * @param <V> the second data type
 */
public class SimpleTuple<U, V> implements Tuple<U,V> {
    /** The first data. */
    private final U u;
    /** The second data. */
    private final V v;

    /**
     *
     * @param u the first data element
     * @param v the second data element
     */
    public SimpleTuple( U u, V v ) {
        this.u = Objects.requireNonNull( u, "u must not be null" );
        this.v = Objects.requireNonNull( v , "v must not be null" );
    }

    @Override
    public U getU() {
        return u;
    }

    @Override
    public V getV() {
        return v;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + u.hashCode(); // u and v are not null
        hash = 47 * hash + v.hashCode();
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if( obj == this ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        @SuppressWarnings( "unchecked" ) // safe here due to class check
        final Tuple<U, V> other = (Tuple<U, V>) obj;
        if( !this.u.equals( other.getU() ) ) {
            return false;
        }
        return this.v.equals( other.getV() );
    }

    @Override
    public String toString() {
        return  String.format( "(%s,%s,)", u.toString(), v.toString() );
    }
}
