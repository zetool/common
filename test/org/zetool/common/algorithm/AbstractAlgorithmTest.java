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

import java.util.LinkedList;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class AbstractAlgorithmTest {
  
  private static class FakeAlgorithm extends Algorithm<Object, Object> {

    public FakeAlgorithm() {
    }

    public FakeAlgorithm( String name ) {
      super( name );
    }

    @Override public Object runAlgorithm( Object problem ) { return null; }
  }

  @Test
  public void testSimple() {
    assertAlgorithm( new FakeAlgorithm(), "FakeAlgorithm" );
    assertAlgorithm( new FakeAlgorithm( "TestFakeAlgorithm" ), "TestFakeAlgorithm" );
  }

  @Test
  public void testAnonymous() {
    assertAlgorithm( new Algorithm<Object, Object>() {
      @Override protected Object runAlgorithm( Object problem ) { return null; }
    }, "Algorithm" );
  }

  private void assertAlgorithm( Algorithm<?, ?> t, String name ) {
    assertThat( t.getName(), is( equalTo( name ) ) );
    assertThat( t.getParameterSet(), is( not( nullValue() ) ) );
    assertState( t, Algorithm.State.UNINITIALIZED );
  }
  
  @Test
  public void testListener() {
    Algorithm<Object,?> t = new FakeAlgorithm();
    final List<AlgorithmEvent> events = new LinkedList<>();
    t.addAlgorithmListener( event -> events.add( event ) );
    t.setProblem( new Object() );

    t.run();
    
    assertThat( events.get( 0 ), instanceOf(AlgorithmStartedEvent.class));
    assertThat( events.get( events.size() - 1 ), instanceOf(AlgorithmTerminatedEvent.class));
  }
  
  private void assertState( Algorithm<?, ?> t, Algorithm.State state ) {
    assertThat( t.getState(), is( equalTo( state ) ) );
    switch( state ) {
      case SOLVED:
        fail( "Not implemented: " + state );
        break;
      case SOLVING:
        fail( "Not implemented: " + state );
        break;
      case SOLVING_FAILED:
        fail( "Not implemented: " + state );
        break;
      case UNINITIALIZED:
        assertThat( t.isPaused(), is( false ) );
        assertThat( t.isProblemInitialized(), is( false ) );
        assertThat( t.isProblemSolved(), is( false ) );
        assertThat( t.isRunning(), is( false ) );
        break;
      case WAITING:
        fail( "Not implemented: " + state );
        break;
      default:
        fail( "Undefined state in test: " + state );
    }
  }
}
