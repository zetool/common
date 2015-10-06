package org.zetool.common.localization;

import java.util.Locale;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestAbstractLocalization {
    AbstractLocalization loc = new AbstractLocalization("org.zetool.common.localization.test", Locale.ENGLISH) {

    };

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
}
