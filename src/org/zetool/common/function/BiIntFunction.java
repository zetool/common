package org.zetool.common.function;

import java.util.Objects;
import java.util.function.Function;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
@FunctionalInterface
public interface BiIntFunction<R> {

    R apply(int t, int u);

/**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of output of the {@code after} function, and of the
     *           composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     */
    default <V> BiIntFunction<V> andThen(Function<? super R, ? extends V> after) {
        return (int t, int u) -> Objects.requireNonNull(after).apply(apply(t, u));
    }
    
    public static void main(String[] a) {
        Function<Double, Double> square = (Double t) -> t * t;
        BiIntFunction<Double> sum = (int t, int u) -> (double) t + u;
        System.out.println(sum.andThen(square).apply(3, 4));
    }
}
