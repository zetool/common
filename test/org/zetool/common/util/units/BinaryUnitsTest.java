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

import org.junit.Test;
import static org.zetool.common.util.units.BinarySIUnitsTest.assertAll;
import static org.zetool.common.util.units.BinarySIUnitsTest.assertList;

/**
 * 
 * @author Jan-Philipp Kappmeier
 */
public class BinaryUnitsTest {
  /** A list by all multiples in the unit scale. */
  private static final BinaryUnits[] units = new BinaryUnits[] {BinaryUnits.Bit, BinaryUnits.Byte, BinaryUnits.KiB,
    BinaryUnits.MiB, BinaryUnits.GiB, BinaryUnits.TiB, BinaryUnits.PiB, BinaryUnits.EiB, BinaryUnits.ZiB,
    BinaryUnits.YiB};
    
  @Test
  public void testList() {
    assertList( units );
  }
  
  @Test
  public void testAll() {
    assertAll( units );
  }
}
