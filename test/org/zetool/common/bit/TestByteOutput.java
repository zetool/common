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
package org.zetool.common.bit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestByteOutput {
    private final static byte[] bytes1 = {82, 8, -57, 61};
    private final static byte[] bytes2 = {-13, 24, 99, -7};
    private final static byte[] bytes3 = {82, 8};

    @Test
    public void testCombineBytes() {
        byte[] result = ByteOutput.combineBytes(bytes1, bytes2, bytes3);
        byte[] expected = {82, 8, -57, 61, -13, 24, 99, -7, 82, 8};
        assertThat(result, is(equalTo(expected)));
    }
    
    @Test
    public void testTotalLength() {
        int result = ByteOutput.totalLength(bytes1, bytes2, bytes3);
        assertThat(result, is(equalTo(10)));
    }

    @Test
    public void testTotalLengthEmpty() {
        int result = ByteOutput.totalLength();
        assertThat(result, is(equalTo(0)));
    }
}
