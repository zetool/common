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
package org.zetool.common.bit;

import java.util.stream.Stream;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ByteOutput {

    static byte[] combineBytes(byte[]... bytes) {
        int completeLength = totalLength(bytes);
        byte[] output = new byte[completeLength];
        int startIndex = 0;
        for(byte[] current : bytes ) {
            System.arraycopy(current, 0, output, startIndex, current.length);
            startIndex += current.length;
        }
        return output;
    }
    
    static int totalLength(byte[]... bytes) {
        return Stream.of(bytes).map(x -> x.length).mapToInt(Integer::intValue).sum();
    }
    
}
