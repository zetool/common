package org.zetool.common.util.units;

import org.junit.Ignore;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
@Ignore
public class ConversionTest {
  /**
   * A test unit consisting of 4 units: Base, TwoBase, FourBase, TenBase.
   */
  public static class TestUnit implements UnitScale<TestUnit> {

    @Override
    public boolean isInRange( double value ) {
      throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TestUnit getBetterUnit( double value ) {
      throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getBetterUnitValue( double value ) {
      throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TestUnit getSmaller() {
      throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TestUnit getLarger() {
      throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSymbol() {
      throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
      throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getRange() {
      throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }
  }
}
