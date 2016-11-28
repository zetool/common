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

/**
 * An enumeration containing time units following the SI system for times less than a seconds. For larger times, the
 * non-SI units minutes, hours etc. are used.
 */
public class TimeUnits extends AbstractUnitScale<TimeUnits> {
  /** One picosecond equals 10^-12 seconds. */
  public static final TimeUnits PICO_SECOND = new TimeUnits( "ps", "picosecond", null );
  /** One picosecond equals 10^-9 seconds. */
  public static final TimeUnits NANO_SECOND = new TimeUnits( "ns", "nanoseconds", PICO_SECOND );
  /** One picosecond equals 10^-6 seconds. */
  public static final TimeUnits MICRO_SECOND = new TimeUnits( "µs", "mycroseconds", NANO_SECOND );
  /** One picosecond equals 10^-3 seconds. */
  public static final TimeUnits MILLI_SECOND = new TimeUnits( "ms", "milliseconds", MICRO_SECOND );
  /** The international second. */
  public static final TimeUnits SECOND = new TimeUnits( "s", "seconds", 60, MILLI_SECOND );
  /** The non-SI unit for 60 seconds. */
  public static final TimeUnits MINUTE = new TimeUnits( "min", "minutes", 60, SECOND );
  /** The non-SI unit for 60 minutes. */
  public static final TimeUnits HOUR = new TimeUnits( "h", "hour", 24, MINUTE );
  /** The non-SI unit for 24 hours. */
  public static final TimeUnits DAY = new TimeUnits( "d", "day", 365.25, HOUR );
  /** The non-SI unit for a Julian year which is 365.25 days. */
  public static final TimeUnits YEAR = new TimeUnits( "a", "year", 100, DAY );
  /** The rarely used unit for a century, which are approximately 100 years. */
  public static final TimeUnits CENTURY = new TimeUnits( "c", "century", 10, YEAR );
  /** A Julian millennium consists of 365,250 days. */
  public static final TimeUnits MILLENIUM = new TimeUnits( "m", "millennium", Integer.MAX_VALUE, CENTURY );

  static {
    PICO_SECOND.setSmaller(PICO_SECOND );
    PICO_SECOND.setLarger(NANO_SECOND );
    NANO_SECOND.setLarger(MICRO_SECOND );
    MICRO_SECOND.setLarger(MILLI_SECOND );
    MILLI_SECOND.setLarger(SECOND );
    SECOND.setLarger(MINUTE );
    MINUTE.setLarger(HOUR );
    HOUR.setLarger(DAY );
    DAY.setLarger(YEAR );
    YEAR.setLarger(CENTURY );
    CENTURY.setLarger( MILLENIUM );
    MILLENIUM.setLarger( MILLENIUM );
  }

  /**
   * Initializes the time unit scale with the correct values.
   *
   * @param symbol the short representation string
   * @param name the long representation string
   * @param range how much of the unit is the next larger scale
   * @param smaller the predecessor unit. Note that successor units are initialized in a static-initializer block
   */
  private TimeUnits( String symbol, String name, double range, TimeUnits smaller ) {
    super( symbol, name, range, smaller );
  }

  /**
   * Initializes the time unit with the correct values.
   *
   * @param symbol the short representation string
   * @param name the long representation string
   * @param smaller the predecessor unit. Note that successor units are initialized in a static-initializer block
   */
  private TimeUnits( String symbol, String name, TimeUnits smaller ) {
    this( symbol, name, 1000, smaller );
  }

  @Override
  public TimeUnits getBetterUnit( double value ) {
    return UnitScale.getNextBetter( this, value );
  }

  @Override
  public double getBetterUnitValue( double value ) {
    return UnitScale.getNextBetterValue( this, value );
  }
}
