package org.zetool.common.function;

import java.util.function.Function;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestBiIntFunction {
    @Test
    public void test() {
        Function<Integer, Integer> square = t -> t * t;
        BiIntFunction<Integer> sum = (int t, int u) -> t + u;
        assertThat(sum.andThen(square).apply(3, 4), is(equalTo(49)));
        assertThat(sum.andThen(square).apply(2, 3), is(equalTo(25)));
    }
}
