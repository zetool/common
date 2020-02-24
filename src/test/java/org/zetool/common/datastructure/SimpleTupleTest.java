package org.zetool.common.datastructure;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.zetool.common.datastructure.SimpleTuple.asTuple;

import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class SimpleTupleTest {

    @Test
    public void staticTupleCreation() {
        String valueA = new String("some text");
        Object valueB = new Object();

        Tuple<String, Object> tupleUnderTest = asTuple(valueA, valueB);

        assertThat(tupleUnderTest.getU(), is(sameInstance(valueA)));
        assertThat(tupleUnderTest.getV(), is(sameInstance(valueB)));
    }
}
