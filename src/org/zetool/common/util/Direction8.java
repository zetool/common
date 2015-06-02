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

package org.zetool.common.util;

/**
 * This enumerates directions on an integral 2 dimensional room such as a raster. Each direction has offsets for the
 * {@literal x} and {@literal y}-direction in \{-1,0,1\}. The {@literal x}-coordinate increases in the <b>Right</b>
 * direction while the {@literal y}-coordinate increases in the <b>downwards</b> direction. Hence, if only positive
 * coordinates are allowed, the point {@literal (0,0)} lies in the up-most, left-most corner.
 *
 * @author Daniel R. Schmidt
 * @author Jan-Philipp Kappmeier
 */
public enum Direction8 {
  /** The upper direction. */
  Top( 0, -1, 0 ),
  /** The upper Right direction. */
  TopRight( 1, -1, 1 ),
  /** The Right direction. */
  Right( 1, 0, 2 ),
  /** The lower Right direction. */
  DownRight( 1, 1, 3 ),
  /** The lower direction. */
  Down( 0, 1, 4 ),
  /** The lower Left direction. */
  DownLeft( -1, 1, 5 ),
  /** The Left direction. */
  Left( -1, 0, 6 ),
  /** The upper Left direction. */
  TopLeft( -1, -1, 7 );

  /** The offset value in {@code x}-direction. */
  private final int xOffset;
  /** The offset value in {@code y}-direction. */
  private final int yOffset;
  /** The id of the direction, from 0 to 7. Used to access the array. */
  private final int id;

  /**
   * The constructor for the direction. It needs to be called with the offsets and the opposite direction. It also sets
   * the opposite direction for the opposite direction.
   * <p>
   * @param xOffset          the offset value in {@code x}-direction
   * @param yOffset          the offset value in {@code y}-direction
   * @param inverseDirection the opposite direction
   */
  private Direction8( int xOffset, int yOffset, int id ) {
    this.xOffset = xOffset;
    this.yOffset = yOffset;
    this.id = id;
  }

  /**
   * Returns the offset value in {@code x}-direction.
   * 
   * @return the offset value in {@code x}-direction
   */
  public final int xOffset() {
    return xOffset;
  }

  /**
   * Returns the offset value in {@code x}-direction.
   * 
   * @return the offset value in {@code x}-direction
   */
  public final int yOffset() {
    return yOffset;
  }

  /**
   * Gives the opposite direction.
   * 
   * @return the opposite direction
   */
  public final Direction8 invert() {
    return values()[(id + 4) % 8];
  }

  /**
   * Returns the enumeration item corresponding to the given {@literal x}- and {@literal y}-offsets.
   * 
   * @param x {@literal x}-offset
   * @param y {@literal y}-offset
   * @return the enumeration item corresponding to the given x- and y-offsets.
   */
  public static Direction8 getDirection( final int x, final int y ) {
    for( Direction8 dir : Direction8.values() ) {
      if( dir.xOffset == x && dir.yOffset == y ) {
        return dir;
      }
    }
    throw new AssertionError( "No valid direction indicated by: " + x + "," + y );
  }

  public Direction8 getClockwise() {
    return values()[(id + 1) % 8];
  }

  public Direction8 getCounterClockwise() {
    return values()[(id - 1 + 8) % 8];
  }

  public double distance() {
    switch( id ) {
      case 0:
      case 2:
      case 4:
      case 6:
        return 1;
      default:
        return Math.sqrt( 2 );
    }
  }
}
