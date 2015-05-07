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
    AbstractUnit myUnit = new AbstractUnit( "ur", "unit rep", 10, null ) {

      @Override
      public Object getBetterUnit( double value ) {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
      }

      @Override
      public double getBetterUnitValue( double value ) {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
      }

    };
    assertThat( myUnit.getName(), is( equalTo( "ur" ) ) );
    assertThat( myUnit.getLongName(), is( equalTo( "unit rep" ) ) );
    assertThat( myUnit.getRange(), is( closeTo( 10, 0.1 ) ) );
  }
}
