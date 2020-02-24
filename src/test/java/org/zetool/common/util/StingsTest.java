package org.zetool.common.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class StingsTest {
    
    @Test
    public void differentLength() {
        assertThat(Strings.isPermutation("a", "long"), is(false));
    }
    
    @Test
    public void caseDistinction() {
        assertThat(Strings.isPermutation("God", "dog"), is(false));
        assertThat(Strings.isPermutation("god", "dog"), is(true));
    }
}
