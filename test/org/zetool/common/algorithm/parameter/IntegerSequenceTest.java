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
public class IntegerSequenceTest {

    @Test(expected = IllegalArgumentException.class)
    public void failureInvalidArguments() {
        IntegerSequence ds = new IntegerSequence(1, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void failureInvalidArgumentsWithSkip() {
        IntegerSequence ds = new IntegerSequence(1, 0, 0.5);
    }
    
    @Test
    public void singleElement() {
        IntegerSequence ds = new IntegerSequence(1, 1);
        assertThat(ds.size(), is(equalTo(1)));
        assertThat(ds, contains(1));
    }
    
    @Test
    public void multipleElements() {
        IntegerSequence ds = new IntegerSequence(1, 2);
        assertThat(ds.size(), is(equalTo(2)));
        assertThat(ds, contains(1, 2));
    }
    
    @Test
    public void nonDefaultSkip() {
        IntegerSequence ds = new IntegerSequence(1, 2, 0.4);
        assertThat(ds.size(), is(equalTo(4)));
        assertThat(ds, contains(1, 1, 2, 2));
    }
    
    @Test(expected = NoSuchElementException.class)
    public void failureAccessingNextElement() {
        IntegerSequence ds = new IntegerSequence(1, 2, 0.6);
        assertThat(ds.size(), is(equalTo(3)));

        Iterator<Integer> it = ds.iterator();
        assertThat(it.next(), is(equalTo(1)));
        assertThat(it.next(), is(equalTo(2)));
        assertThat(it.next(), is(equalTo(2)));
        assertThat(it.hasNext(), is(false));

        it.next();
    }}
