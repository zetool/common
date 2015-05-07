
package org.zetool.common.util.units;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <U>
 */
public abstract class AbstractUnit<U> implements UnitScale<U> {
  private final String rep;
  private final String longRep;
  private U previous;
  private U next;
  private final double toNext;

  /**
   * Initializes the binary unit with the correct values.
   *
   * @param rep      the short representation string
   * @param longRep  the long representation string
   * @param toNext   how much of the unit is the next larger scale
   * @param previous the predecessor unit. Note that successor units are initialized in a static-initializer block
   */
  protected AbstractUnit( String rep, String longRep, double toNext, U previous ) {
    this.rep = rep;
    this.longRep = longRep;
    this.toNext = toNext;
    this.previous = previous;
  }

  @Override
  public double getRange() {
    return toNext;
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
    return previous;
  }

  protected final void setSmaller( U previous ) {
    this.previous = previous;
  }

  @Override
  public U getLarger() {
    return next;
  }
  
  protected final void setLarger( U next ) {
    this.next = next;
  }

  @Override
  public String getName() {
    return rep;
  }

  @Override
  public String getLongName() {
    return longRep;
  }

  /**
   * Returns the short representation of the time unit.
   * 
   * @return the short representation of the time unit
   */
  @Override
  public String toString() {
    return rep;
  }
}
