package org.zetool.common.datastructure;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class Triple<U, V, W> {

    protected U u;
    protected V v;
    protected W w;

    public Triple( U u, V v, W w ) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    public U u() {
        return u;
    }

    public V v() {
        return v;
    }

    public W w() {
        return w;
    }

}
