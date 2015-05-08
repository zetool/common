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
 * IEC units for binary multiples of of bits and bytes. Units are as described in
 * http://physics.nist.gov/cuu/Units/binary.html with the addition of 'bits' as smalles unit.
 *
 * @author Jan-Philipp Kappmeier
 */
public class BinaryUnits extends AbstractUnit<BinaryUnits> {
  public static final BinaryUnits Bit = new BinaryUnits( "Bits", "Bits", 8, null );
  public static final BinaryUnits Byte = new BinaryUnits( "Bytes", "Bytes", Bit );
  public static final BinaryUnits KiB = new BinaryUnits( "KiB", "Kibibyte", Byte );
  public static final BinaryUnits MiB = new BinaryUnits( "MiB", "Mebibyte", KiB );
  public static final BinaryUnits GiB = new BinaryUnits( "GiB", "Gibibyte", MiB );
  public static final BinaryUnits TiB = new BinaryUnits( "TiB", "Tebibyte", GiB );
  public static final BinaryUnits PiB = new BinaryUnits( "PiB", "Pebibyte", TiB );
  public static final BinaryUnits EiB = new BinaryUnits( "EiB", "Exbibyte", PiB );
  public static final BinaryUnits ZiB = new BinaryUnits( "ZiB", "Zebibyte", EiB );
  public static final BinaryUnits YiB = new BinaryUnits( "YiB", "Yobibyte", ZiB );
  
  static {
    Bit.setSmaller( Bit );
    Bit.setLarger( Byte );
    Byte.setLarger( KiB );
    KiB.setLarger( MiB );
    MiB.setLarger( GiB );
    GiB.setLarger( TiB );
    TiB.setLarger( PiB );
    PiB.setLarger( EiB );
    EiB.setLarger( ZiB );
    ZiB.setLarger( YiB );
    YiB.setLarger( YiB );
  }

  /**
   * Initializes the binary unit with the correct values.
   *
   * @param symbol the short representation string
   * @param name the long representation string
   * @param range how much of the unit is the next larger scale
   * @param smaller the predecessor unit. Note that successor units are initialized in a static-initializer block
   */
  private BinaryUnits( String symbol, String name, double range, BinaryUnits smaller ) {
    super(symbol, name, range, smaller );
  }

  private BinaryUnits( String symbol, String name, BinaryUnits smaller ) {
    this( symbol, name, 1024, smaller );
  }

  @Override
  public BinaryUnits getBetterUnit( double value ) {
    return Helper.getNextBetter( this, value );
  }

  @Override
  public double getBetterUnitValue( double value ) {
    return Helper.getNextBetterValue( this, value );
  }
}
