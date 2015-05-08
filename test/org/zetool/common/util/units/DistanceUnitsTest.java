package org.zetool.common.util.units;

import org.junit.Test;
import static org.zetool.common.util.units.TestBinarySIUnits.assertAll;
import static org.zetool.common.util.units.TestBinarySIUnits.assertList;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class DistanceUnitsTest {
  /** A list by all multiples in the unit scale. */
  private static final DistanceUnits[] units = new DistanceUnits[]{DistanceUnits.NanoMeter, DistanceUnits.MicroMeter,
    DistanceUnits.MilliMeter, DistanceUnits.CentiMeter, DistanceUnits.DeciMeter, DistanceUnits.Meter,
    DistanceUnits.KiloMeter};

  @Test
  public void testList() {
    assertList( units );
  }

  @Test
  public void testAll() {
    assertAll( units );
  }
}
