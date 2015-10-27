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

package org.zetool.common.util.units;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class AbstractUnitTest {

  @Test
  public void testUnitCreation() {
    AbstractUnitScale myUnit = new AbstractUnitScale( "ur", "unit rep", 10, null ) {

      @Override
      public Object getBetterUnit( double value ) {
        throw new UnsupportedOperationException( "Not supported yet." );
      }

      @Override
      public double getBetterUnitValue( double value ) {
        throw new UnsupportedOperationException( "Not supported yet." );
      }

    };
    assertThat( myUnit.getSymbol(), is( equalTo( "ur" ) ) );
    assertThat( myUnit.getName(), is( equalTo( "unit rep" ) ) );
    assertThat( myUnit.getRange(), is( closeTo( 10, 0.1 ) ) );
  }
}
