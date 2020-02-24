package org.zetool.common.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class BoundsTest {

    @Test
    public void clockWise() {
        assertThat(Bounds.UPPER.getClockwise(), is(equalTo(Bounds.RIGHT)));
        assertThat(Bounds.RIGHT.getClockwise(), is(equalTo(Bounds.LOWER)));
        assertThat(Bounds.LOWER.getClockwise(), is(equalTo(Bounds.LEFT)));
        assertThat(Bounds.LEFT.getClockwise(), is(equalTo(Bounds.UPPER)));
    }

    @Test
    public void counterClockWise() {
        assertThat(Bounds.UPPER.getCounterClockwise(), is(equalTo(Bounds.LEFT)));
        assertThat(Bounds.LEFT.getCounterClockwise(), is(equalTo(Bounds.LOWER)));
        assertThat(Bounds.LOWER.getCounterClockwise(), is(equalTo(Bounds.RIGHT)));
        assertThat(Bounds.RIGHT.getCounterClockwise(), is(equalTo(Bounds.UPPER)));
    }
    
    @Test
    public void invert() {
        assertThat(Bounds.UPPER.invert(), is(equalTo(Bounds.LOWER)));
        assertThat(Bounds.LEFT.invert(), is(equalTo(Bounds.RIGHT)));
        assertThat(Bounds.LOWER.invert(), is(equalTo(Bounds.UPPER)));
        assertThat(Bounds.RIGHT.invert(), is(equalTo(Bounds.LEFT)));
    }
    
    @Test
    public void coordinates() {
        assertThat(Bounds.UPPER.xOffset(), is(equalTo(0)));
        assertThat(Bounds.LOWER.xOffset(), is(equalTo(0)));
        assertThat(Bounds.LEFT.xOffset(), is(equalTo(-1)));
        assertThat(Bounds.RIGHT.xOffset(), is(equalTo(1)));

        assertThat(Bounds.UPPER.yOffset(), is(equalTo(-1)));
        assertThat(Bounds.LOWER.yOffset(), is(equalTo(1)));
        assertThat(Bounds.LEFT.yOffset(), is(equalTo(0)));
        assertThat(Bounds.RIGHT.yOffset(), is(equalTo(0)));        
    }
}
