/* zet evacuation tool copyright (c) 2007-14 zet evacuation team
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.zetool.common.util.units.Quantity;
import org.zetool.common.util.units.TimeUnits;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class AlgorithmTerminatedEventTest {

    @Test
    public void testRangesWorks() {
        AbstractAlgorithm<Object, Object> testInstance = new AbstractAlgorithm<Object, Object>() {

            @Override
            protected Object runAlgorithm(Object problem) {
                return null;
            }
        };

        Quantity<TimeUnits> runtime = new Quantity<>(3, TimeUnits.Microsecond);
        AlgorithmTerminatedEvent<Object, Object> progressEvent = new AlgorithmTerminatedEvent<>(testInstance, runtime);
        assertThat(progressEvent.getRuntime(), is(equalTo(runtime)));
    }

    @Test
    public void testRa() {
        AbstractAlgorithm<Object, Object> testAlgorithm = new AbstractAlgorithm<Object, Object>() {
            private int calls = 0;

            @Override
            protected Object runAlgorithm(Object problem) {
                return null;
            }

            @Override
            protected long getTime() {
                calls++;
                if (calls == 1) {
                    return 10;
                } else if (calls == 2) {
                    return 20;
                }
                throw new AssertionError("getTime called too often.");
            }
        };
        testAlgorithm.setProblem(new Object());
        testAlgorithm.run();
        AlgorithmTerminatedEvent<Object, Object> progressEvent = new AlgorithmTerminatedEvent<>(testAlgorithm);
        assertThat(progressEvent.getRuntime(), is(equalTo(new Quantity<>(10, TimeUnits.MilliSeconds))));
    }

}
