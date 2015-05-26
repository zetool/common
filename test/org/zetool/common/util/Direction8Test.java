package org.zetool.common.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.zetool.common.util.Direction8.Down;
import static org.zetool.common.util.Direction8.DownLeft;
import static org.zetool.common.util.Direction8.DownRight;
import static org.zetool.common.util.Direction8.Left;
import static org.zetool.common.util.Direction8.Right;
import static org.zetool.common.util.Direction8.Top;
import static org.zetool.common.util.Direction8.TopLeft;
import static org.zetool.common.util.Direction8.TopRight;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class Direction8Test {

  @Test
  public void testRelativeDirection() {
    assertThat( Direction8.getDirection( -1, 0 ), is( equalTo( Left ) ) );
    assertThat( Direction8.getDirection( 1, 0 ), is( equalTo( Right ) ) );
    assertThat( Direction8.getDirection( 0, -1 ), is( equalTo( Top ) ) );
    assertThat( Direction8.getDirection( 0, 1 ), is( equalTo( Down ) ) );
    assertThat( Direction8.getDirection( -1, -1 ), is( equalTo( TopLeft ) ) );
    assertThat( Direction8.getDirection( 1, -1 ), is( equalTo( TopRight ) ) );
    assertThat( Direction8.getDirection( -1, 1 ), is( equalTo( DownLeft ) ) );
    assertThat( Direction8.getDirection( 1, 1 ), is( equalTo( DownRight ) ) );
  }
  
  @Test( expected = AssertionError.class )
  public void testInvalidDirection() {
    Direction8.getDirection( -2, 0 );
  }

  @Test
  public void testClockwiseRotation() {
    assertThat( Left.getClockwise(), is( equalTo( TopLeft ) ) );
    assertThat( TopLeft.getClockwise(), is( equalTo( Top ) ) );
    assertThat( Top.getClockwise(), is( equalTo( TopRight ) ) );
    assertThat( TopRight.getClockwise(), is( equalTo( Right ) ) );
    assertThat( Right.getClockwise(), is( equalTo( DownRight ) ) );
    assertThat( DownRight.getClockwise(), is( equalTo( Down ) ) );
    assertThat( Down.getClockwise(), is( equalTo( DownLeft ) ) );
    assertThat( DownLeft.getClockwise(), is( equalTo( Left ) ) );
  }

  @Test
  public void testCounterClockwiseRotation() {
    assertThat( Left.getCounterClockwise(), is( equalTo( DownLeft ) ) );
    assertThat( TopLeft.getCounterClockwise(), is( equalTo( Left ) ) );
    assertThat( Top.getCounterClockwise(), is( equalTo( TopLeft ) ) );
    assertThat( TopRight.getCounterClockwise(), is( equalTo( Top ) ) );
    assertThat( Right.getCounterClockwise(), is( equalTo( TopRight ) ) );
    assertThat( DownRight.getCounterClockwise(), is( equalTo( Right ) ) );
    assertThat( Down.getCounterClockwise(), is( equalTo( DownRight ) ) );
    assertThat( DownLeft.getCounterClockwise(), is( equalTo( Down ) ) );
  }

  @Test
  public void testDistance() {
    assertThat( Left.distance(), is( closeTo( 1, 1e-10 ) ) );
    assertThat( Right.distance(), is( closeTo( 1, 1e-10 ) ) );
    assertThat( Top.distance(), is( closeTo( 1, 1e-10 ) ) );
    assertThat( Down.distance(), is( closeTo( 1, 1e-10 ) ) );
    assertThat( TopLeft.distance(), is( closeTo( 1.41421356237, 1e-10 ) ) );
    assertThat( TopRight.distance(), is( closeTo( 1.41421356237, 1e-10 ) ) );
    assertThat( DownLeft.distance(), is( closeTo( 1.41421356237, 1e-10 ) ) );
    assertThat( DownRight.distance(), is( closeTo( 1.41421356237, 1e-10 ) ) );
  }
  
  @Test
  public void testOpposite() {
    assertThat( Left.invert(), is( equalTo( Right )));
    assertThat( TopLeft.invert(), is( equalTo( DownRight )));
    assertThat( Top.invert(), is( equalTo( Down )));
    assertThat( TopRight.invert(), is( equalTo( DownLeft )));
    assertThat( Right.invert(), is( equalTo( Left )));
    assertThat( DownRight.invert(), is( equalTo( TopLeft )));
    assertThat( Down.invert(), is( equalTo( Top )));
    assertThat( DownLeft.invert(), is( equalTo( TopRight )));
  }
}
