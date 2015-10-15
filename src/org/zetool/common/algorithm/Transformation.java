/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.zetool.common.algorithm;

/**
 * Executes an algorithm on a transformed problem instance and afterwards also transforms the solution. As a parameter
 * the {@code Transformation} needs an algorithm computing a solution for the transformed problem.
 *
 * Use case of the {@code Transformation} is the existence of an {@code T -&gt; R} algorithm when actually a problem of
 * type {@code P} should be used as input to compute a solution of type {@code S}. To be efficient it is required that
 * the original problem can be easily transformed into the format for the existing algorithm and the solution can
 * as easy be transformed back. Typically such problem/solution transformation comprise wrappers etc.
 *
 * The two transformation methods {@link #transformProblem(java.lang.Object)} and
 * {@link #transformSolution(java.lang.Object)} just perform a cast in the default implementation.
 * 
 * @author Jan-Philipp Kappmeier
 * @param <P> The initial problem type.
 * @param <T> The transformed problem
 * @param <R> The solution computed for the transformed problem.
 * @param <S> The transformed solution.
 */
public class Transformation<P, T, R, S> extends AbstractAlgorithm<P, S> implements AlgorithmListener {

    protected AbstractAlgorithm<T, R> algorithm;

    public AbstractAlgorithm<T, R> getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AbstractAlgorithm<T, R> algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    protected S runAlgorithm(P originalProblem) {
        T reducedProblem = transformProblem(originalProblem);
        algorithm.setProblem(reducedProblem);
        algorithm.addAlgorithmListener((AlgorithmListener) this);
        algorithm.run();
        algorithm.removeAlgorithmListener(this);
        if( algorithm.isProblemSolved()) {
            R solutionToTheTransformedProblem = algorithm.getSolution();
            return transformSolution(solutionToTheTransformedProblem);            
        } else {
            throw new RuntimeException(algorithm.getCause());
        }
    }

    @Override
    public void eventOccurred(AbstractAlgorithmEvent event) {
        fireEvent(event);
    }

    protected T transformProblem(P originalProblem) {
        return (T) originalProblem;
    }

    protected S transformSolution(R transformedSolution) {
            S sol = (S)transformedSolution;
            return sol;
    }
}
