
package org.zetool.common.util.units;

import org.junit.Test;
import static org.zetool.common.util.units.TestBinarySIUnits.assertAll;
import static org.zetool.common.util.units.TestBinarySIUnits.assertList;

/**
 * 
 * @author Jan-Philipp Kappmeier
 */
public class TestBinaryUnits {
  /** A list by all multiples in the unit scale. */
  private static final BinaryUnits[] units = new BinaryUnits[] {BinaryUnits.Bit, BinaryUnits.Byte, BinaryUnits.KiB,
    BinaryUnits.MiB, BinaryUnits.GiB, BinaryUnits.TiB, BinaryUnits.PiB, BinaryUnits.EiB, BinaryUnits.ZiB,
    BinaryUnits.YiB};
    
  @Test
  public void testList() {
    assertList( units );
  }
  
  @Test
  public void testAll() {
    assertAll( units );
  }
}
