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
     * Returns a composed function that first applies this integer bi-function to its input and
     * afterwards applies the {@code after} function to the result.
     *
     * @param <S> the type of the composed function
     * @param after the function applied after the int bi-function
     * @return a composed function that first applies this integer bi-function and then {@code after}
     * @throws NullPointerException if after is null
     */
    default <S> BiIntFunction<S> andThen(Function<? super R, ? extends S> after) {
        return (int t, int u) -> Objects.requireNonNull(after).apply(apply(t, u));
    }
}
