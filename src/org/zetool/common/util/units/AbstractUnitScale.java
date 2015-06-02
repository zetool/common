
package org.zetool.common.util.units;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <U> the unit type handled by the unit scale
 */
public abstract class AbstractUnitScale<U> implements UnitScale<U> {
  /** The short symbol of the unit. */
  private final String symbol;
  /** The name of the unit. */
  private final String name;
  /** The next smaller unit of measure. */
  private U smaller;
  /** The larger unit of measure. */
  private U larger;
  /** The range until the larger unit becomes applicable. */
  private final double range;

  /**
   * Initializes the binary unit with the correct values.
   *
   * @param symbol the short representation string
   * @param name the long representation string
   * @param range how much of the unit is the next larger scale
   * @param previous the preceeding unit. Note that successor units are initialized in a static-initializer block
   */
  protected AbstractUnitScale( String symbol, String name, double range, U previous ) {
    this.symbol = symbol;
    this.name = name;
    this.range = range;
    this.smaller = previous;
  }

  @Override
  public double getRange() {
    return range;
  }

  @Override
  public boolean isInRange( double value ) {
    return getBetterUnit( value ) == this;
  }

  @Override
  public abstract U getBetterUnit( double value );

  @Override
  public abstract double getBetterUnitValue( double value );

  @Override
  public U getSmaller() {
    return smaller;
  }

  protected final void setSmaller( U smaller ) {
    this.smaller = smaller;
  }

  @Override
  public U getLarger() {
    return larger;
  }
  
  protected final void setLarger( U larger ) {
    this.larger = larger;
  }

  @Override
  public String getSymbol() {
    return symbol;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * Returns the short representation of the time unit.
   * 
   * @return the short representation of the time unit
   */
  @Override
  public String toString() {
    return symbol;
  }
}
