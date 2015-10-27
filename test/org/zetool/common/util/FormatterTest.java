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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package org.zetool.common.util;

import java.awt.Color;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class FormatterTest {

  
  /**
   * Tests if the formatter for percents uses the descripted number of digits
   */
  @Test
  public void formatPercentTest() {
    assertEquals( "Testing negative", 6, Formatter.formatPercent( -0.01 ).length() );
    assertEquals( "Testing 0", 5, Formatter.formatPercent( 0 ).length() );
    assertEquals( "Testing float", 6, Formatter.formatPercent( 0.5f ).length() );
    assertEquals( "a third", 6, Formatter.formatPercent( 0.3 ).length() );
    assertEquals( "Testing double", 6, Formatter.formatPercent( 0.5d ).length() );
    assertEquals( "Testing 1", 7, Formatter.formatPercent( 1 ).length() );
    assertEquals( "one and a third", 7, Formatter.formatPercent( 1.3 ).length() );
  }
  
  @Test
  public void fillLeadingZerosTest() {
    assertThat( Formatter.fillLeadingZeros( 3, 1), is( equalTo( "3" ) ) );
    assertThat( Formatter.fillLeadingZeros( 3, 2), is( equalTo( "03" ) ) );
    assertThat( Formatter.fillLeadingZeros( 3, 3), is( equalTo( "003" ) ) );
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testFillLeadingZerosException() {
    Formatter.fillLeadingZeros( 3, 0 );
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testFillLeadingNegative() {
    Formatter.fillLeadingZeros( 3, -1 );
  }
  
  @Test
  public void testRgbToHex() {
    assertThat( Formatter.rgbToHex( 255, 255, 255 ).toUpperCase(), is( equalTo( "#FFFFFF" ) ) );
    assertThat( Formatter.rgbToHex( 255, 0, 0 ).toUpperCase(), is( equalTo( "#FF0000" ) ) );
    assertThat( Formatter.rgbToHex( 0, 255, 0 ).toUpperCase(), is( equalTo( "#00FF00" ) ) );
    assertThat( Formatter.rgbToHex( 0, 0, 255 ).toUpperCase(), is( equalTo( "#0000FF" ) ) );
    assertThat( Formatter.rgbToHex( 0, 0, 0 ).toUpperCase(), is( equalTo( "#000000" ) ) );
    assertThat( Formatter.rgbToHex( 255, 175, 175 ).toUpperCase(), is( equalTo( "#FFAFAF" ) ) );    
  }
  
  /**
   * Checks hat negative values and values larger than 255 are rejected.
   */
  @Test
  public void testRgbToHexBounds() {
    int count = 0;
    int[][] negative = { {-1, 0, 0}, {0, -1, 0}, {0, 0, -1}, {256, 0, 0}, {0, 256, 0}, {0, 0, 256} };
    for( int i = 0; i < negative.length; ++ i) {
      try {
        String toUpperCase = Formatter.rgbToHex( negative[i][0], negative[i][1], negative[i][2] ).toUpperCase();
      } catch( IllegalArgumentException ex ) {
        count++;
      }
    }
    assertThat( negative.length, is( equalTo( count ) ) );
  }
  
  /**
   * Tests the color to hex converter.
   */
  @Test
  public void testColorToHEX() {
    assertThat( Formatter.colorToHex( Color.white ).toUpperCase(), is( equalTo( "#FFFFFF" ) ) );
    assertThat( Formatter.colorToHex( Color.red ).toUpperCase(), is( equalTo( "#FF0000" ) ) );
    assertThat( Formatter.colorToHex( Color.green ).toUpperCase(), is( equalTo( "#00FF00" ) ) );
    assertThat( Formatter.colorToHex( Color.blue ).toUpperCase(), is( equalTo( "#0000FF" ) ) );
    assertThat( Formatter.colorToHex( Color.black ).toUpperCase(), is( equalTo( "#000000" ) ) );
    assertThat( Formatter.colorToHex( Color.pink ).toUpperCase(), is( equalTo( "#FFAFAF" ) ) );
  }
}
