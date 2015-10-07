package org.zetool.common.localization;

import java.util.Locale;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestAbstractLocalization {
    private AbstractLocalization loc;
    
    @Before
    public void init() {
        loc = new AbstractLocalization("org.zetool.common.localization.test", Locale.ENGLISH) {
        };
    }

    @Test
    public void testBasicCreation() {
        assertThat(loc.toString(), is(not(nullValue())));
    }
    
    @Test
    public void testPrefix() {
        loc.setPrefix("entry.with.some.");
        assertThat(loc.getString("prefix"), is(equalTo("test-value")));
        loc.setPrefix("entry.with.other.");
        assertThat(loc.getString("prefix"), is(equalTo("other-value")));
        loc.clearPrefix();
        assertThat(loc.getString("prefix"), is(equalTo("test-value")));
        loc.clearPrefix();
        assertThat(loc.getString("prefix"), is(equalTo("prefix")));
    }
    
    @Test
    public void testAccessWithoutPrefix() {
        loc.setPrefix("entry.with.some.");
        assertThat(loc.getStringWithoutPrefix("entry.with.some.prefix"), is(equalTo("test-value")));
        assertThat(loc.getStringWithoutPrefix("entry.with.other.prefix"),
                is(equalTo("other-value")));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testClearFails() {
        loc.clearPrefix();
    }
    
    @Test
    public void testClearFailsAfterAdding() {
        loc.setPrefix("some prefix");
        loc.clearPrefix();
        try {
            loc.clearPrefix();
        } catch (IllegalStateException e) {
            return;
        }
        fail();
    }
    
    @Test
    public void testCommonLocalization() {
        Localization defaultLoc = CommonLocalization.LOC;
        assertThat(defaultLoc.getString("general.NotSupportedException"), is(equalTo("The operation is not supported.")));
    }
}
