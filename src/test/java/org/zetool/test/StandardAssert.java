package org.zetool.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.function.Function;

/**
 * Collection of helper methods for assertions on standard member function.
 *
 * @author Jan-Philipp Kappmeier
 */
public class StandardAssert {

    public static <T, R> void assertDataAccess(T object, Function<T, R> dataAccess, R expected) {
        assertThat(dataAccess.apply(object), is(equalTo(expected)));
    }

}
