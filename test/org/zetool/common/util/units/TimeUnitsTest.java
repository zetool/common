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
import org.junit.Test;
import static org.zetool.common.util.units.BinarySIUnitsTest.assertAll;
import static org.zetool.common.util.units.BinarySIUnitsTest.assertList;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TimeUnitsTest {

  private final static double EPS = 10e-8;

  /**
   * A list by all multiples in the unit scale.
   */
  private static final TimeUnits[] units = new TimeUnits[]{TimeUnits.PicoSeconds, TimeUnits.NanoSeconds,
    TimeUnits.Microsecond, TimeUnits.MilliSeconds, TimeUnits.Seconds, TimeUnits.Minutes, TimeUnits.Hours,
    TimeUnits.Days, TimeUnits.Years, TimeUnits.Centuries, TimeUnits.Millennia};

  @Test
  public void testList() {
    assertList( units );
  }

  @Test
  public void testAll() {
    assertAll( units );
  }

  /**
   * Tests the formatting for time units.
   */
  @Test
  public void testUnits() {
    Quantity<TimeUnits> ret;
    final int secondsOfYear = 31557600;
    // check if the number of seconds of a year is computed correct (note that it is not the multiple of 365!
    ret = Conversion.unit( secondsOfYear, TimeUnits.Seconds );
    assertEquals( 1.0, ret.getValue(), EPS );
    assertEquals( TimeUnits.Years, ret.getUnit() );

    // what if, if they are minutes?
    ret = Conversion.unit( secondsOfYear, TimeUnits.Minutes );
    assertEquals( 60.0, ret.getValue(), EPS );
    assertEquals( TimeUnits.Years, ret.getUnit() );

    //1 micro second = 1000 nano seconds = 0,000 001 seconds
    ret = Conversion.unit( 1000, TimeUnits.NanoSeconds );
    assertEquals( 1.0, ret.getValue(), EPS );
    assertEquals( TimeUnits.Microsecond, ret.getUnit() );

    ret = Conversion.unit( 0.000001, TimeUnits.Seconds );
    assertEquals( 1.0, ret.getValue(), EPS );
    assertEquals( TimeUnits.Microsecond, ret.getUnit() );

    //1 pico second = 0,000 000 000 001 seconds
    ret = Conversion.unit( 1001, TimeUnits.PicoSeconds );
    assertEquals( 1.001, ret.getValue(), EPS );
    assertEquals( ret.getUnit(), TimeUnits.NanoSeconds );

    ret = Conversion.unit( 0.000000000001, TimeUnits.Seconds );
    assertEquals( "Converting seconds to pico seconds", 1.0, ret.getValue(), EPS );
    assertEquals( TimeUnits.PicoSeconds, ret.getUnit() );

    ret = Conversion.unit( 0.999, TimeUnits.PicoSeconds );
    assertEquals( 0.999, ret.getValue(), EPS );
    assertEquals( TimeUnits.PicoSeconds, ret.getUnit() );
  }
}
