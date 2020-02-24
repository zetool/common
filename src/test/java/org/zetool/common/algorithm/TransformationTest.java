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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.zetool.common.algorithm;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TransformationTest {
    final Integer input = 345;
    final Double output = 2.0;
    AbstractAlgorithm<Integer, Double> a = new AbstractAlgorithm<Integer, Double>() {

        @Override
        protected Double runAlgorithm(Integer problem) {
            if(problem.equals(input)) {
                return output;
            }
            throw new AssertionError("Bad input to algorithm.");
        }
    };

    @Test
    public void defaultImplementation() {
        Transformation<Integer, Integer, Double, Double> transformation = new Transformation<>();
        transformation.setAlgorithm(a);
        transformation.setProblem(input);
        transformation.call();
        Double solution = transformation.getSolution();
        assertThat(solution, is(closeTo(output, 10e-8)));
    }

    @Test(expected = ClassCastException.class)
    public void defaultSolutionTransformationFails() {
        Transformation<Integer, Integer, Double, Boolean> transformation = new Transformation<>();
        transformation.setAlgorithm(a);
        transformation.setProblem(input);
        Boolean b = transformation.call();
    }

    @Test
    public void defaultProblemTransformationFailsAsAlgorithm() {
        Transformation<Double, Integer, Double, Double> transformation = new Transformation<>();
        transformation.setAlgorithm(a);
        transformation.setProblem(2.0);
        transformation.runAlgorithm();
        assertThat(transformation.isProblemSolved(), is(equalTo(false)));
        Throwable t = transformation.getCause();
        assertThat(t.getCause(), is(instanceOf(ClassCastException.class)));
    }
}
