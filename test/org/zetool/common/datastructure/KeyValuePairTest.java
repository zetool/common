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

package org.zetool.common.datastructure;

import java.util.ArrayList;
import java.util.Collections;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.hamcrest.Matchers;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class KeyValuePairTest {
    @Test
    public void testKeyValueInitialization() {
        KeyValuePair<Integer,String> pair = new KeyValuePair<>(3, "whatever");
        assertThat(pair.getKey(), is(equalTo(3) ) );
        assertThat(pair.getValue(), is(equalTo("whatever" ) ) );
        assertThat(pair.toString(), is(not(Matchers.nullValue())));
    }
    
    /**
     * Tests that the {@link Comparable} implementation of the key value pair works.
     */
    @Test
    public void testComparator() {        
        final ArrayList<KeyValuePair<Integer,String>> numbers = new ArrayList<>();
        numbers.add(new KeyValuePair<>(3, "Three"));
        numbers.add(new KeyValuePair<>(4, "Four"));
        numbers.add(new KeyValuePair<>(2, "Two"));
        numbers.add(new KeyValuePair<>(1, "One"));

        Collections.sort( numbers );

        for( int i = 1; i <= 4; ++i ) {
            assertThat(numbers.get(i-1).getKey(), is(equalTo(i)));
        }
    }
}
