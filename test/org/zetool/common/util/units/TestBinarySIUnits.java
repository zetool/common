
package org.zetool.common.util.units;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestBinarySIUnits {
  /** A list by all multiples in the unit scale. */
  private static final BinarySIUnits[] units = new BinarySIUnits[] {BinarySIUnits.B, BinarySIUnits.kB, BinarySIUnits.MB,
      BinarySIUnits.GB, BinarySIUnits.TB, BinarySIUnits.PB};
  
  @Test
  public void testList() {
    assertList( units );
  }
  
  @Test
  public void testAll() {
    assertAll( units );
  }
  
  public static <U extends UnitScale<U>> void assertList( U[] units )  {
    assertThat( units[1].getSmaller(), is( equalTo( units[0] ) ) );
    for( int i = 1; i < units.length; ++i ) {
      assertThat( units[i].getSmaller(), is( equalTo( units[i-1] ) ) );
      assertThat( "Larger for " + units[i-1] + " not correct.", units[i-1].getLarger(), is( equalTo( units[i] ) ) );
    }
    assertThat( units[units.length-2].getLarger(), is( equalTo( units[units.length-1] ) ) );
  }

    
  public static <U extends UnitScale<U>> void assertAll( U[] units )  {
    U start = units[0];
    U end = units[units.length-1];
    
    U current = start;
    while( current.getLarger() != current ) {
      current = current.getLarger();
    }
    assertThat( current, is( equalTo( end ) ) );
    
    current = end;
    while( current.getSmaller() != current ) {
      current = current.getSmaller();
    }
    assertThat( current, is( equalTo( start ) ) );
  }
}
