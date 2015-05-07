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

package org.zetool.common.util.units;

import org.zetool.common.util.Helper;

/**
 * An enumeration containing time units following the SI system for times less than a seconds. For larger times, the
 * non-SI units minutes, hours etc. are used.
 */
public class TimeUnits extends AbstractUnit<TimeUnits> {
  /** One picosecond equals 10^-12 seconds. */
	public static final TimeUnits PicoSeconds = new TimeUnits( "ps", "picosecond", null );
	/** One picosecond equals 10^-9 seconds. */
	public static final TimeUnits NanoSeconds = new TimeUnits( "ns", "nanoseconds", PicoSeconds );
	/** One picosecond equals 10^-6 seconds. */
	public static final TimeUnits Microsecond = new TimeUnits( "µs", "mycroseconds", NanoSeconds );
	/** One picosecond equals 10^-3 seconds. */
	public static final TimeUnits MilliSeconds = new TimeUnits( "ms", "milliseconds", Microsecond );
	/** The international second. */
	public static final TimeUnits Seconds = new TimeUnits( "s", "seconds", 60, MilliSeconds );
	/** The non-SI unit for 60 seconds. */
	public static final TimeUnits Minutes = new TimeUnits( "min", "minutes", 60, Seconds );
	/** The non-SI unit for 60 minutes. */
	public static final TimeUnits Hours = new TimeUnits( "h", "hour", 24, Minutes );
	/** The non-SI unit for 24 hours. */
	public static final TimeUnits Days = new TimeUnits( "d", "day", 365.25, Hours );
	/** The non-SI unit for a Julian year which is 365.25 days. */
	public static final TimeUnits Years = new TimeUnits( "a", "year", 100, Days );
	/** The rarely used unit for a century, which are approximately 100 years. */
	public static final TimeUnits Centuries = new TimeUnits( "c", "century", 10, Years );
	/** A Julian millennium consists of 365,250 days. */
	public static final TimeUnits Millenia = new TimeUnits( "m", "millenium", Integer.MAX_VALUE, Centuries );

  static {
    PicoSeconds.setSmaller( PicoSeconds );
    PicoSeconds.setLarger( NanoSeconds );
    NanoSeconds.setLarger( Microsecond );
    Microsecond.setLarger( MilliSeconds );
    MilliSeconds.setLarger( Seconds );
    Seconds.setLarger( Minutes );
    Minutes.setLarger( Hours );
    Hours.setLarger( Days );
    Days.setLarger( Years );
    Years.setLarger( Centuries );
    Centuries.setLarger( Millenia );
    Millenia.setLarger( Millenia );
  }

  /**
   * Initializes the time unit scale with the correct values.
   *
   * @param rep     the short representation string
   * @param longRep the long representation string
   * @param toNext  how much of the unit is the next larger scale
   * @param smaller     the predecessor unit. Note that successor units are initialized in a static-initializer block
   */
  private TimeUnits( String rep, String longRep, double toNext, TimeUnits smaller ) {
    super(rep, longRep, toNext, smaller );
  }

  /**
   * Initializes the time unit with the correct values.
   *
   * @param rep     the short representation string
   * @param longRep the long representation string
   * @param pre     the predecessor unit. Note that successor units are initialized in a static-initializer block
   */
  private TimeUnits( String rep, String longRep, TimeUnits pre ) {
    this( rep, longRep, 1000, pre );
  }

  @Override
  public TimeUnits getBetterUnit( double value ) {
    return Helper.getNextBetter( this, value );
  }

  @Override
  public double getBetterUnitValue( double value ) {
    return Helper.getNextBetterValue( this, value );
  }
}
