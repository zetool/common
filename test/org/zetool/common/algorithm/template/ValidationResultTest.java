package org.zetool.common.algorithm.template;

import java.util.Arrays;
import java.util.function.Supplier;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ValidationResultTest {

    @Test
    public void init() {
        final String aMessage = "test message";

        ValidationResult vr = new ValidationResult(true, aMessage);

        assertParameter(vr::getMessage, aMessage);
        assertParameter(vr::isSuccessful, true);

        vr = new ValidationResult(false, aMessage);
        assertParameter(vr::isSuccessful, false);
    }

    @Test
    public void combination() {
        final String message1 = "result 1 message";
        final String message2 = "result 2 message";

        ValidationResult result1Successfull = new ValidationResult(true, message1);
        ValidationResult result1Unsuccessfull = new ValidationResult(false, message1);
        ValidationResult result2Successfull = new ValidationResult(true, message2);
        ValidationResult result2Unsuccessfull = new ValidationResult(false, message2);

        ValidationResult combineSuccessful = result1Successfull.combine(result2Successfull);
        assertThat(combineSuccessful.getMessage(), containsString(message1));
        assertThat(combineSuccessful.getMessage(), containsString(message2));
        assertParameter(combineSuccessful::isSuccessful, true);
        for (ValidationResult vr : Arrays.asList(new ValidationResult[]{result1Successfull.combine(result2Unsuccessfull),
            result1Unsuccessfull.combine(result2Unsuccessfull), result2Successfull.combine(result1Unsuccessfull)})) {
            assertThat(vr.getMessage(), containsString(message1));
            assertThat(vr.getMessage(), containsString(message2));
            assertParameter(vr::isSuccessful, false);

        }

    }

    public static <T> void assertParameter(Supplier<T> s, T expected) {
        assertThat(s.get(), is(equalTo(expected)));
    }
}
