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
package org.zetool.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.zetool.common.util.Helper.in;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class HelperTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testIsBetween() {
    assertThat( Helper.isBetween( 'a', 'a', 'z' ), is( true ) );
    assertThat( Helper.isBetween( 'z', 'a', 'z' ), is( true ) );
    assertThat( Helper.isBetween( 'm', 'a', 'z' ), is( true ) );
    assertThat( Helper.isBetween( 'a', 'b', 'z' ), is( false ) );
    assertThat( Helper.isBetween( 'z', 'a', 'y' ), is( false ) );
  }
  
  @Test
  public void testIn() {
    ArrayList<Integer> numbers = new ArrayList<>();
    numbers.add( 1 );
    numbers.add( 2 );
    numbers.add( 3 );
    
    int sum = 0;
    for( Integer i : in( numbers.iterator() ) ) {
      sum += i;
    }

    assertThat( sum, is( equalTo( 6 ) ) );
  }

  @Test
  public void testInIteratorAtEnd() {
    ArrayList<Integer> numbers = new ArrayList<>();
    numbers.add( 1 );
    
    Iterator<Integer> iterator = numbers.iterator();
    
    int count = 0;
    for( Integer i : in(iterator ) ) {
      count++;
    }
    assertThat( count, is( equalTo( 1 ) ) );
    assertThat( iterator.hasNext(), is( false ) );
    for( Integer i : in( iterator ) ) {
      throw new AssertionError( "Should be empty!" );
    }
  }
  
  @Test
  public void testInUsableOnce() {
    ArrayList<Integer> numbers = new ArrayList<>();
    numbers.add( 1 );

    Iterator<Integer> iterator = numbers.iterator();
    Iterable<Integer> iterable = in( iterator );

    for( Integer i : iterable ) {
    }
    exception.expect( IllegalStateException.class );
    for( Integer i : iterable ) {
    }
  }
}
