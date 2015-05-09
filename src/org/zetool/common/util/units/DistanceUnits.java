
package org.zetool.common.util.units;

import org.zetool.common.util.Helper;

/**
 * SI unit distances (based on meter) with the SI prefixes.
 *
 * @author Jan-Philipp Kappmeier
 */
public class DistanceUnits extends AbstractUnit<DistanceUnits> {
  /** One nanometer equals 10^-9 meter. */
  public static final DistanceUnits NanoMeter = new DistanceUnits( "nm", "nanometer", 10, null );
  /** One micrometer equals 10^-6 meter. */
  public static final DistanceUnits MicroMeter = new DistanceUnits( "µm", "micrometer", 10, NanoMeter );
  /** One millimeter equals 10^-3 meter. */
  public static final DistanceUnits MilliMeter = new DistanceUnits( "m", "millimeter", 10, MicroMeter );
  /** One centimeter equals 10^-2 meter. */
  public static final DistanceUnits CentiMeter = new DistanceUnits( "cm", "centimeter", 10, MilliMeter );
  /** One decimeter equals 10^-1 meter. */
  public static final DistanceUnits DeciMeter = new DistanceUnits( "dm", "decimeter", 10, CentiMeter );
  /** The meter is the basic SI unit for measuring distances. */
  public static final DistanceUnits Meter = new DistanceUnits( "m", "meter", 1000, DeciMeter );
  /** One kilometer equals 10^3 meter. */
  public static final DistanceUnits KiloMeter = new DistanceUnits( "km", "kilometer", Integer.MAX_VALUE, Meter );

  static {
    NanoMeter.setSmaller( NanoMeter );
    NanoMeter.setLarger( MicroMeter );
    MicroMeter.setLarger( MilliMeter );
    MilliMeter.setLarger( CentiMeter );
    CentiMeter.setLarger( DeciMeter );
    DeciMeter.setLarger( Meter );
    Meter.setLarger( KiloMeter );
    KiloMeter.setLarger( KiloMeter );
  }

  /**
   * An enumeration containing time units following the SI system for times less than a seconds. For larger times, the
   * non-SI units minutes, hours etc. are used.
   *
   * @param symbol the short representation string
   * @param name the long representation string
   * @param range how much of the unit is the next larger scale
   * @param smaller the predecessor unit. Note that successor units are initialized in a static-initializer block
   */
  public DistanceUnits( String symbol, String name, double range, DistanceUnits smaller ) {
    super( symbol, name, range, smaller );
  }

  @Override
  public DistanceUnits getBetterUnit( double value ) {
    return UnitScale.getNextBetter( this, value );
  }

  @Override
  public double getBetterUnitValue( double value ) {
    return UnitScale.getNextBetterValue( this, value );
  }
}
