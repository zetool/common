
package org.zetool.common.util.units;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import org.jmock.Mockery;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class QuantityTest {
  
  private interface FakeUnit extends UnitScale<FakeUnit> { };
  private final Mockery context = new Mockery();
  private final FakeUnit unit = context.mock(FakeUnit.class);
  
  @Test
  public void testBasicQuantityConstruction() {
    final Quantity<FakeUnit> t = new Quantity<>( 3, unit );
    assertThat( unit, is( equalTo( t.getUnit() ) ) );
    assertThat( 3L, is( equalTo( t.getIntegralValue() ) ) );
    assertThat( 3., is( equalTo( t.getValue() ) ) );
    assertThat( unit.toString(), is( not( nullValue() ) ) );
  }
  
  /**
   * Tests that the quantity is integral if actual integral values are passed.
   */
  @Test
  public void testIntegralQuantity() {
    final long[] values = new long[]{Long.MIN_VALUE, -1, 0, 1, 2, Long.MAX_VALUE};
    for( long value : values ) {
      final Quantity<FakeUnit> t = new Quantity<>( value, unit );
      assertThat( t.isIntegral(), is( true ) );
    }
  }

  /**
   * Tests that the quantity is integral if integral double values are passed.
   */
  @Test
  public void testIntegralQuantityDouble() {
    final double[] doubleValues = new double[]{Long.MIN_VALUE, -1.0, 0., 1., 2., Long.MAX_VALUE, Double.MAX_VALUE};
    for( double doubleValue : doubleValues ) {
      final Quantity<FakeUnit> t = new Quantity<>( doubleValue, unit );
      assertThat( doubleValue + " is not integral", t.isIntegral(), is( true ) );
    }
  }

  /**
   * Tests that the quantity is not integral if non-integral values are passed.
   */
  @Test
  public void testNonIntegral() {
    final double[] doubleValues = new double[]{Double.MIN_VALUE, -.5, .5, 2.5,};
    for( double doubleValue : doubleValues ) {
      final Quantity<FakeUnit> t = new Quantity<>( doubleValue, unit );
      assertThat( doubleValue + " is integral", t.isIntegral(), is( false ) );
    }
  }

  @Test( expected = IllegalStateException.class )
  public void testException() {
    final Quantity<FakeUnit> t = new Quantity<>( 2.5, unit );
    t.getIntegralValue();
  }

  @Test
  public void testEquals() {
    final Quantity<FakeUnit> original = new Quantity<>( 1, unit );
    assertThat( "Cannot be equal to null", original, not( equalTo( null ) ) );
    assertThat( "Not equal with different class", original, not( equalTo( new QuantityTest() ) ) );
  }

  @Test
  public void testEqualsIntegral() {
    final Quantity<FakeUnit> original = new Quantity<>( 1, unit );
    final Quantity<FakeUnit> copy = new Quantity<>( 1, unit );

    assertThat( original, is( not( sameInstance( copy ) ) ) );
    assertThat( "Quantity as to be equal to itself", original, is( equalTo( original ) ) );
    assertThat( "Two nodes are equal", original, is( equalTo( copy ) ) );
    assertThat( "Two nodes are equal", copy, is( equalTo( original ) ) );

    final int hashCode = original.hashCode();
    assertThat( "Hash code not equal", hashCode, is( equalTo( copy.hashCode() ) ) );
    assertThat( "Hash code does not change", hashCode, is( equalTo( original.hashCode() ) ) );
  }

  @Test
  public void testEqualsIntegralDouble() {
    final Quantity<FakeUnit> original = new Quantity<>( 1, unit );
    final Quantity<FakeUnit> copy = new Quantity<>( 1.0, unit );

    assertThat( original, is( not( sameInstance( copy ) ) ) );
    assertThat( "Quantity as to be equal to itself", original, is( equalTo( original ) ) );
    assertThat( "Two nodes are equal", original, is( equalTo( copy ) ) );
    assertThat( "Two nodes are equal", copy, is( equalTo( original ) ) );

    final int hashCode = original.hashCode();
    assertThat( "Hash code not equal", hashCode, is( equalTo( copy.hashCode() ) ) );
    assertThat( "Hash code does not change", hashCode, is( equalTo( original.hashCode() ) ) );
  }

  @Test
  public void testEqualsDouble() {
    final Quantity<FakeUnit> original = new Quantity<>( 1.0, unit );
    final Quantity<FakeUnit> copy = new Quantity<>( 1.0, unit );

    assertThat( original, is( not( sameInstance( copy ) ) ) );
    assertThat( "Quantity as to be equal to itself", original, is( equalTo( original ) ) );
    assertThat( "Two nodes are equal", original, is( equalTo( copy ) ) );
    assertThat( "Two nodes are equal", copy, is( equalTo( original ) ) );

    final int hashCode = original.hashCode();
    assertThat( "Hash code not equal", hashCode, is( equalTo( copy.hashCode() ) ) );
    assertThat( "Hash code does not change", hashCode, is( equalTo( original.hashCode() ) ) );
  }

  @Test
  public void testNotEqualsIntegral() {
    final Quantity<FakeUnit> original = new Quantity<>( 1, unit );
    final Quantity<FakeUnit> copy = new Quantity<>( 2, unit );

    assertThat( original, is( not( sameInstance( copy ) ) ) );
    assertThat( "Two nodes are equal", original, is( not( equalTo( copy ) ) ) );
    assertThat( "Two nodes are equal", copy, is( not( equalTo( original ) ) ) );

    final int hashCode = original.hashCode();
    assertThat( "Integral hash code equal", hashCode, is( not( equalTo( copy.hashCode() ) ) ) );
  }

  @Test
  public void testNotEqualsIntegralDouble() {
    final Quantity<FakeUnit> original = new Quantity<>( 1, unit );
    final Quantity<FakeUnit> copy = new Quantity<>( Math.nextUp( 1 ), unit );

    assertThat( original, is( not( sameInstance( copy ) ) ) );
    assertThat( "Two nodes are equal", original, is( not( equalTo( copy ) ) ) );
    assertThat( "Two nodes are equal", copy, is( not( equalTo( original ) ) ) );

    final int hashCode = original.hashCode();
    assertThat( "Integral hash code equal", hashCode, is( not( equalTo( copy.hashCode() ) ) ) );
  }

  @Test
  public void testNotEqualsDouble() {
    final Quantity<FakeUnit> original = new Quantity<>( 0.0, unit );
    final Quantity<FakeUnit> copy = new Quantity<>( Math.nextUp( 0.0 ), unit );

    assertThat( original, is( not( sameInstance( copy ) ) ) );
    assertThat( "Two nodes are equal", original, is( not( equalTo( copy ) ) ) );
    assertThat( "Two nodes are equal", copy, is( not( equalTo( original ) ) ) );

    final int hashCode = original.hashCode();
    assertThat( "Integral hash code equal", hashCode, is( not( equalTo( copy.hashCode() ) ) ) );
  }
  
  @Test
  public void testAdditionIntegral() {
    final Quantity<FakeUnit> quantity1 = new Quantity<>( 2, unit );
    final Quantity<FakeUnit> quantity2 = new Quantity<>( 7, unit );
    final Quantity<FakeUnit> sum = quantity1.add( quantity2 );

    assertThat( 9L, is( equalTo( sum.getIntegralValue() ) ) );
  }

  @Test
  public void testAdditionIntegralDouble() {
    final Quantity<FakeUnit> quantity1 = new Quantity<>( 2, unit );
    final Quantity<FakeUnit> quantity2 = new Quantity<>( 3.3, unit );
    final Quantity<FakeUnit> sum = quantity1.add( quantity2 );

    assertThat( sum.isIntegral(), is( false ) );
    assertThat( 5.3, is( equalTo( sum.getValue() ) ) );
  }

  @Test
  public void testAdditionDouble() {
    final Quantity<FakeUnit> quantity1 = new Quantity<>( 2.5, unit );
    final Quantity<FakeUnit> quantity2 = new Quantity<>( 3.3, unit );
    final Quantity<FakeUnit> sum = quantity1.add( quantity2 );

    assertThat( sum.isIntegral(), is( false ) );
    assertThat( 5.8, is( equalTo( sum.getValue() ) ) );
  }

  @Test
  public void testSubtractionIntegral() {
    final Quantity<FakeUnit> quantity1 = new Quantity<>( 2, unit );
    final Quantity<FakeUnit> quantity2 = new Quantity<>( 7, unit );
    final Quantity<FakeUnit> sum = quantity1.sub( quantity2 );

    assertThat( -5L, is( equalTo( sum.getIntegralValue() ) ) );
  }

  @Test
  public void testSubtractionIntegralDouble() {
    final Quantity<FakeUnit> quantity1 = new Quantity<>( 2, unit );
    final Quantity<FakeUnit> quantity2 = new Quantity<>( 3.3, unit );
    final Quantity<FakeUnit> sum = quantity1.sub( quantity2 );

    assertThat( sum.isIntegral(), is( false ) );
    assertThat( -1.3, is( closeTo( sum.getValue(), 1e-10 ) ) );
  }

  @Test
  public void testSubtractionDouble() {
    final Quantity<FakeUnit> quantity1 = new Quantity<>( 2.5, unit );
    final Quantity<FakeUnit> quantity2 = new Quantity<>( 3.3, unit );
    final Quantity<FakeUnit> sum = quantity1.sub( quantity2 );

    assertThat( sum.isIntegral(), is( false ) );
    assertThat( -0.8, is( closeTo( sum.getValue(), 1e-10 ) ) );
  }
  
  @Test( expected = UnsupportedOperationException.class )
  public void testAddNotSupported() {
    final FakeUnit differentUnit = context.mock(FakeUnit.class, "another");
    final Quantity<FakeUnit> quantity1 = new Quantity<>( 2.5, unit );
    final Quantity<FakeUnit> quantity2 = new Quantity<>( 3.3, differentUnit );
    quantity1.add( quantity2 );
  }
  
  @Test( expected = UnsupportedOperationException.class )
  public void testSubNotSupported() {
    final FakeUnit differentUnit = context.mock(FakeUnit.class, "another");
    final Quantity<FakeUnit> quantity1 = new Quantity<>( 2.5, unit );
    final Quantity<FakeUnit> quantity2 = new Quantity<>( 3.3, differentUnit );
    quantity1.sub( quantity2 );
  }
}
