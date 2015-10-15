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

import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import org.jmock.Mockery;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class AlgorithmProgressEventTest {
    private final Mockery context = new Mockery();

    @Test
    public void testRangesWorks() {
        Algorithm algorithmMock = context.mock(Algorithm.class);
        AlgorithmProgressEvent progressEvent = new AlgorithmProgressEvent(algorithmMock, 0.3);
        assertThat(progressEvent.getAlgorithm(), is(equalTo(algorithmMock)));
        assertThat(progressEvent.getProgress(), is(closeTo(0.3, 10e-8)));
        assertThat(progressEvent.getProgressAsInteger(), is(equalTo(30)));
    }

    @Test
    public void testBoundaryRanges() {
        Algorithm algorithmMock = context.mock(Algorithm.class);
        assertThat( new AlgorithmProgressEvent(algorithmMock, 0), is(CoreMatchers.notNullValue()));
        assertThat( new AlgorithmProgressEvent(algorithmMock, 1), is(CoreMatchers.notNullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeProgressFails() {
        assertThat( new AlgorithmProgressEvent(context.mock(Algorithm.class), -0.0001), is(CoreMatchers.notNullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLargeProgressFails() {
        assertThat( new AlgorithmProgressEvent(context.mock(Algorithm.class), 1.0001), is(CoreMatchers.notNullValue()));
    }
}
