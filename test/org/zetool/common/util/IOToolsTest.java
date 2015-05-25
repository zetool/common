/* zet evacuation tool copyright (c) 2007-14 zet evacuation team
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

import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class IOToolsTest {

  @Test
  public void testParseCommandStringEmpty() {
    String command = "";
    List<String> commands = IOTools.parseCommandString( command );
    assertThat( commands.isEmpty(), is( true ) );
  }

  @Test
  public void testParseCommandStringSingle() {
    String command = "\"single\"";
    List<String> commands = IOTools.parseCommandString( command );
    assertThat( commands.size(), is( equalTo( 1 ) ) );
    assertThat( commands.get( 0 ), is( equalTo( "single" ) ) );
  }

  @Test
  public void testParseCommandStringMultiple() {
    String command = "\"first\" -a \"more\" last";
    List<String> commands = IOTools.parseCommandString( command );
    assertThat( commands.size(), is( equalTo( 4 ) ) );
    assertThat( commands.get( 0 ), is( equalTo( "first" ) ) );
    assertThat( commands.get( 1 ), is( equalTo( "-a" ) ) );
    assertThat( commands.get( 2 ), is( equalTo( "more" ) ) );
    assertThat( commands.get( 3 ), is( equalTo( "last" ) ) );
  }
  

  @Test
  public void testParseCommandStringSpaces() {
    String command = "\"first\" -a \"with spaces\" last";
    List<String> commands = IOTools.parseCommandString( command );
    assertThat( commands.size(), is( equalTo( 4 ) ) );
    assertThat( commands.get( 0 ), is( equalTo( "first" ) ) );
    assertThat( commands.get( 1 ), is( equalTo( "-a" ) ) );
    assertThat( commands.get( 2 ), is( equalTo( "with spaces" ) ) );
    assertThat( commands.get( 3 ), is( equalTo( "last" ) ) );
  }
}
