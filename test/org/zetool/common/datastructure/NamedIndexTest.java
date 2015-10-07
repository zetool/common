package org.zetool.common.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class NamedIndexTest {
    @Test
    public void testNamedIndexBasics() {
        NamedIndex index = new NamedIndex("indexname", 3);
        assertThat(index.getName(), is(equalTo("indexname")));
        assertThat(index.getIndex(), is(equalTo(3)));
        assertThat(index.toString(), is(notNullValue()));
    }
}
