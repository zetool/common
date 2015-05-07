
package org.zetool.common.util.units;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class DistanceUnits extends AbstractUnit<DistanceUnits> {

  public DistanceUnits( String rep, String longRep, double toNext, DistanceUnits previous ) {
    super( rep, longRep, toNext, previous );
  }

  @Override
  public DistanceUnits getBetterUnit( double value ) {
    throw new UnsupportedOperationException( "Not supported yet." );
  }

  @Override
  public double getBetterUnitValue( double value ) {
    throw new UnsupportedOperationException( "Not supported yet." );
  }
  
}
