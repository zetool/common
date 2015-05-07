/**
 * FormatterTest.java
 * Created: Nov 18, 2010, 1:33:30 PM
 */
package org.zetool.common.util;

import static org.junit.Assert.assertEquals;
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
}
