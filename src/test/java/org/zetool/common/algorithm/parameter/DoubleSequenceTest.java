package org.zetool.common.algorithm.parameter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class DoubleSequenceTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void failureInvalidArguments() {
        DoubleSequence ds = new DoubleSequence(1, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void failureInvalidArgumentsWithSkip() {
        DoubleSequence ds = new DoubleSequence(1, 0, 0.5);
    }
    
    @Test
    public void singleElement() {
        DoubleSequence ds = new DoubleSequence(1.0, 1.0);
        assertThat(ds.size(), is(equalTo(1)));
        assertThat(ds, contains(1.0));
    }
    
    @Test
    public void multipleElements() {
        DoubleSequence ds = new DoubleSequence(1.0, 2.0);
        assertThat(ds.size(), is(equalTo(2)));
        assertThat(ds, contains(1.0, 2.0));
    }
    
    @Test
    public void nonDefaultSkip() {
        DoubleSequence ds = new DoubleSequence(1.0, 2.0, 0.4);
        assertThat(ds.size(), is(equalTo(4)));
        assertThat(ds, contains(1.0, 1.0 + 0.4, 1.0 + 0.4 + 0.4, 2.0));
    }
    
    @Test(expected = NoSuchElementException.class)
    public void failureAccessingNextElement() {
        DoubleSequence ds = new DoubleSequence(1.0, 2.0, 0.6);
        Iterator<Double> it = ds.iterator();
        assertThat(it.next(), is(equalTo(1.0)));
        assertThat(it.next(), is(equalTo(1.6)));
        assertThat(it.next(), is(equalTo(2.0)));
        assertThat(it.hasNext(), is(false));
        it.next();
    }
}
