
package org.zetool.common.datastructure;

import java.util.Objects;

/**
 * A default {@link Tuple} implementation.
 * @author Jan-Philipp Kappmeier
 */
public class AbstractTuple<U, V> implements Tuple<U,V> {
    /** The first data. */
    private final U u;
    /** The second data. */
    private final V v;

    /**
     *
     * @param u
     * @param v
     * @throws NullPointerException if one of the arguments is {@code null}
     */
    public AbstractTuple( U u, V v ) throws NullPointerException {
        this.u = Objects.requireNonNull( u, "u must not be null" );
        this.v = v; //Objects.requireNonNull( v , "v must not be null" );
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
