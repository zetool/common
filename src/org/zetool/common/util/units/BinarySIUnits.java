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
 * Representation of binary SI units, e.g. multiples of 1000 = 10^3 of bytes.
 * @author Jan-Philipp Kappmeier
 */
public class BinarySIUnits extends AbstractUnit<BinarySIUnits> {
  
  public static final BinarySIUnits B = new BinarySIUnits("kB", "Bytes");
  public static final BinarySIUnits kB = new BinarySIUnits("kB", "kilobytes", B);
  public static final BinarySIUnits MB = new BinarySIUnits("MB", "Megabytes", kB);
  public static final BinarySIUnits GB = new BinarySIUnits("GB", "Gigabytes", MB);
  public static final BinarySIUnits TB = new BinarySIUnits("TB", "Terabytes", GB);
  public static final BinarySIUnits PB = new BinarySIUnits("PB", "Petabytes", TB);
  
  static {
    B.setSmaller( B );
    B.setLarger( kB );
    kB.setLarger( MB );
    MB.setLarger( GB );
    GB.setLarger( TB );
    TB.setLarger( PB );
    PB.setLarger( PB );
  }
  
  private  BinarySIUnits( String symbol, String name ) {
    this( symbol, name, null );
  }

  private  BinarySIUnits( String symbol, String name, BinarySIUnits smaller ) {
    super( symbol, name, 1000, smaller);
  }

  @Override
  public BinarySIUnits getBetterUnit( double value ) {
    return Helper.getNextBetter( this, value );
  }

  @Override
  public double getBetterUnitValue( double value ) {
    return Helper.getNextBetterValue( this, value );
  }
}
