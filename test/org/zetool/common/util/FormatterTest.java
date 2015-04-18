/**
 * FormatterTest.java
 * Created: Nov 18, 2010, 1:33:30 PM
 */
package org.zetool.common.util;

import org.zetool.common.util.units.TimeUnits;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class FormatterTest {

	private final static double EPS = 0.000000000000001;

	/** Creates a new instance of {@code FormatterTest}. */
	public FormatterTest() { }

	/**
	 * Tests the formatting for time units.
	 */
	@Test
	public void testUnits() {
		Quantity<TimeUnits> ret;
		final int secondsOfYear = 31557600;
		// check if the number of seconds of a year is computed correct (note that it is not the multiple of 365!
		ret = Formatter.unit( secondsOfYear, TimeUnits.Seconds );
		assertEquals( 1.0, ret.getValue(), EPS );
		assertEquals( TimeUnits.Years, ret.getUnit() );

		// what if, if they are minutes?
		ret = Formatter.unit( secondsOfYear, TimeUnits.Minutes );
		assertEquals( 60.0, ret.getValue(), EPS );
		assertEquals( TimeUnits.Years, ret.getUnit() );


		//1 micro second = 1000 nano seconds = 0,000 001 seconds
		ret = Formatter.unit( 1000, TimeUnits.NanoSeconds );
		assertEquals(  1.0, ret.getValue(), EPS );
		assertEquals( TimeUnits.Microsecond, ret.getUnit() );

		ret = Formatter.unit( 0.000001, TimeUnits.Seconds );
		assertEquals( 1.0, ret.getValue(), EPS );
		assertEquals( TimeUnits.Microsecond, ret.getUnit() );


		//1 pico second = 0,000 000 000 001 seconds
		ret = Formatter.unit( 1001, TimeUnits.PicoSeconds );
		assertEquals( 1.001, ret.getValue(), EPS );
		assertEquals( ret.getUnit(), TimeUnits.NanoSeconds );

		ret = Formatter.unit( 0.000000000001, TimeUnits.Seconds );
		assertEquals( "Converting seconds to pico seconds", 1.0, ret.getValue(), EPS );
		assertEquals( TimeUnits.PicoSeconds, ret.getUnit() );

		ret = Formatter.unit( 0.999, TimeUnits.PicoSeconds );
		assertEquals( 0.999, ret.getValue(), EPS );
		assertEquals( TimeUnits.PicoSeconds, ret.getUnit() );

	}
  
  /**
   * Tests if the formatter for percents uses the descripted number of digits
   */
  @Test
  public void formatPercentTest() {
    assertEquals( "Testing negative", 6, Formatter.formatPercent( -0.01 ).length() );
    assertEquals( "Testing 0", 5, Formatter.formatPercent( 0 ).length() );
    assertEquals( "Testing float", 6, Formatter.formatPercent( 0.5f ).length() );
    assertEquals( "a third", 6, Formatter.formatPercent( 0.3 ).length() );
    assertEquals( "Testing double", 6, Formatter.formatPercent( 0.5d ).length() );
    assertEquals( "Testing 1", 7, Formatter.formatPercent( 1 ).length() );
    assertEquals( "one and a third", 7, Formatter.formatPercent( 1.3 ).length() );
  }
}
