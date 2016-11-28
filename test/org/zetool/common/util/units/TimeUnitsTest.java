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

import static org.junit.Assert.assertEquals;
import static org.zetool.common.util.units.BinarySIUnitsTest.assertAll;
import static org.zetool.common.util.units.BinarySIUnitsTest.assertList;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TimeUnitsTest {

  private final static double EPS = 10e-8;

  /**
   * A list by all multiples in the unit scale.
   */
  private static final TimeUnits[] UNITS = new TimeUnits[]{TimeUnits.PICO_SECOND, TimeUnits.NANO_SECOND,
    TimeUnits.MICRO_SECOND, TimeUnits.MILLI_SECOND, TimeUnits.SECOND, TimeUnits.MINUTE, TimeUnits.HOUR,
    TimeUnits.DAY, TimeUnits.YEAR, TimeUnits.CENTURY, TimeUnits.MILLENIUM};

  @Test
  public void testList() {
    assertList( UNITS );
  }

  @Test
  public void testAll() {
    assertAll( UNITS );
  }

  /**
   * Tests the formatting for time units.
   */
  @Test
  public void testUnits() {
    Quantity<TimeUnits> ret;
    final int secondsOfYear = 31557600;
    // check if the number of seconds of a year is computed correct (note that it is not the multiple of 365!
    ret = Conversion.unit(secondsOfYear, TimeUnits.SECOND );
    assertEquals( 1.0, ret.getValue(), EPS );
    assertEquals(TimeUnits.YEAR, ret.getUnit() );

    // what if, if they are minutes?
    ret = Conversion.unit(secondsOfYear, TimeUnits.MINUTE );
    assertEquals( 60.0, ret.getValue(), EPS );
    assertEquals(TimeUnits.YEAR, ret.getUnit() );

    //1 micro second = 1000 nano seconds = 0,000 001 seconds
    ret = Conversion.unit(1000, TimeUnits.NANO_SECOND );
    assertEquals( 1.0, ret.getValue(), EPS );
    assertEquals(TimeUnits.MICRO_SECOND, ret.getUnit() );

    ret = Conversion.unit(0.000001, TimeUnits.SECOND );
    assertEquals( 1.0, ret.getValue(), EPS );
    assertEquals(TimeUnits.MICRO_SECOND, ret.getUnit() );

    //1 pico second = 0,000 000 000 001 seconds
    ret = Conversion.unit(1001, TimeUnits.PICO_SECOND );
    assertEquals( 1.001, ret.getValue(), EPS );
    assertEquals(ret.getUnit(), TimeUnits.NANO_SECOND );

    ret = Conversion.unit(0.000000000001, TimeUnits.SECOND );
    assertEquals( "Converting seconds to pico seconds", 1.0, ret.getValue(), EPS );
    assertEquals(TimeUnits.PICO_SECOND, ret.getUnit() );

    ret = Conversion.unit(0.999, TimeUnits.PICO_SECOND );
    assertEquals( 0.999, ret.getValue(), EPS );
    assertEquals(TimeUnits.PICO_SECOND, ret.getUnit() );
  }
}
