package org.zetool.common.bit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.zetool.common.datastructure.SimpleTuple;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class SimpleTupleTest {

    @Test
    public void initialization() {
        Object o1 = new Object();
        Double d1 = (double) 1;
        
        SimpleTuple<Object, Double> tuple = new SimpleTuple<>(o1, d1);
        
        assertThat(tuple.getU(), is(sameInstance(o1)));
        assertThat(tuple.getV(), is(sameInstance(d1)));
    }
    
    @Test(expected = NullPointerException.class)
    public void parameter1NonNull() {
        new SimpleTuple<>(null, (double) 1);
    }
    
    @Test(expected = NullPointerException.class)
    public void parameter2NonNull() {
        new SimpleTuple<>(new Object(), null);
    }
    
    private static class Holder {
        final int a;

        public Holder(int a) {
            this.a = a;
        }

        @Override
        public int hashCode() {
            return a;
        }

        @Override
        public boolean equals(Object obj) {
            Holder other = (Holder)obj;
            return other.a == a;
        }
    }
    
    private static class SpecialTuple<U,V> extends SimpleTuple<U,V> {

        public SpecialTuple(U u, V v) {
            super(u, v);
        }
    }
    
    @Test
    public void comparisonWithEquals() {
        SimpleTuple<Holder, Holder> equal1 = new SimpleTuple<>(new Holder(1), new Holder(2));
        SimpleTuple<Holder, Holder> equal2 = new SimpleTuple<>(new Holder(1), new Holder(2));
        
        SimpleTuple<Holder, Holder> differentU = new SimpleTuple<>(new Holder(3), new Holder(1));
        SimpleTuple<Holder, Holder> differentV = new SimpleTuple<>(new Holder(1), new Holder(3));
        
        
        assertThat(equal1.equals(equal1), is(true));

        assertThat(equal1.equals(differentU), is(false));
        assertThat(equal2.equals(differentV), is(false));
        assertThat(equal1.hashCode(), is(not(equalTo(differentV.hashCode()))));
        
        assertThat(equal1.equals(new SpecialTuple<>(new Holder(1), new Holder(2))), is(false));
        assertThat(new SpecialTuple<>(new Holder(1), new Holder(2)).equals(equal1), is(false));
        
        assertThat(equal1.equals(null), is(false));
        assertThat(equal1.equals(new Object()), is(false));
        
        assertThat(equal1.equals(equal2), is(true));
        assertThat(equal2.equals(equal1), is(true));
        assertThat(equal1.hashCode(), is(equalTo(equal2.hashCode())));
    }
}
