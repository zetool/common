
package org.zetool.common.util;

import de.tu_berlin.coga.common.localization.LocalizationManager;
import de.tu_berlin.coga.common.util.units.UnitScale;
import java.awt.Color;
import java.text.NumberFormat;

/**
 * The class {@code Formatter} is a utility class that provides methods
 * for formatting texts.
 * @author Jan-Philipp Kappmeier
 */
public final class Formatter {

	/** No instantiating of {@code Formatter} possible. */
	private Formatter() { }

	/**
	 * Formats a double value (between 0 and 1) into a percent value, always
	 * showing 2 fraction digits.
	 * @param value the decimal value
	 * @return a string containing the decimal value
	 */
	public static String formatPercent( double value ) {
		NumberFormat nfPercent = LocalizationManager.getManager().getPercentConverter();
		nfPercent.setMaximumFractionDigits( 2 );
		nfPercent.setMinimumFractionDigits( 2 );
		return nfPercent.format( value );
	}

	/**
	 * Computes the correct value and fitting time unit for a given pair of value
	 * and time unit. The result is stored as a {@link UnitScale}.
	 * @param <E> the type of time unit that is returned
	 * @param value the value of the number to be formatted
	 * @param unit the unit of the number
	 * @return the pair containing the transformed value and the fitting unit
	 */
	public static <E extends UnitScale<E>> Quantity<E> unit( double value, E unit ) {
    E currentUnit = unit;
    double restValue = value;
		while( !currentUnit.isInRange( restValue ) ) {
			final double newValue = currentUnit.getBetterUnitValue( restValue );
			currentUnit = currentUnit.getBetterUnit( restValue );
			restValue = newValue;
		}
		return new Quantity<>( restValue, currentUnit );
	}

	/**
	 * Formats a given number of some unit to the most fitting unit.
	 * @param <E> the type of the unit
	 * @param value the value of the number to be formatted
	 * @param unit the unit of the number
	 * @return the string in the calculated unit with one decimal place and the shortcut for the unit
	 */
	public static <E extends UnitScale<E>> String formatUnit( double value, E unit ) {
		return formatUnit( value, unit, 2 );
	}

	/**
	 * Formats a given number of some unit to the most fitting unit.
	 * @param <E> the type of the time unit
	 * @param value the value of the number to be formatted
	 * @param unit the unit of the number
	 * @param digits the number of digits after the comma in the representation
	 * @return the string in the calculated unit with one decimal place and the shortcut for the unit
	 */
	public static <E extends UnitScale<E>> String formatUnit( double value, E unit, int digits ) {
		final Quantity<E> res = unit( value, unit );
		final NumberFormat n = NumberFormat.getInstance();
		n.setMaximumFractionDigits( digits );
		return n.format( res.getValue() ) + " " + res.getUnit().getName();
	}

	/**
	 * Creates a {@code String} containing an integer number with leading zeros.
	 * @param number the number that is converted to string representation
	 * @param digits the digits of the number
	 * @return the number with leading zeros
	 * @throws IllegalArgumentException if the number has to many digits
	 */
  public static String fillLeadingZeros( final int number, final int digits ) throws IllegalArgumentException {
    String ret = Integer.toString( number );
    if( ret.length() > digits ) {
      throw new java.lang.IllegalArgumentException( "Number " + number + " is too long. Only "
              + digits + " digits are allowed." );
    }
    while( ret.length() < digits ) {
      ret = "0" + ret;
    }
    return ret;
  }

  /**
 	 * Converts an RGB cover specified by the amounts of red, green and blue into
	 * an hexadecimal representation leading with a hashtag ("#") as used in HTML.
	 * The values for {@code r}, {@code g}, and {@code b} must be in the interval
	 * from 0 to 255.
	 * @param r the red amount
	 * @param g the green amount
	 * @param b the blue amount
	 * @return the hexadecimal HTML representation of the color
	 */
	public static String rgbToHex( final int r, final int g, final int b ) {
		return String.format( "#%02x%02x%02x", r, g, b );
	}

	/**
	 * Converts an Java {@link Color} into an hexadecimal representation leading
	 * with a hashtag ("#") as used in HTML. The conversion ignores opacity or
	 * alhpa values.
	 * @param c the java color
	 * @return the hexadecimal HTML representation of the color
	 */
	public static String colorToHex( final Color c ) {
		return "#" + Integer.toHexString( c.getRGB() ).substring( 2 );
	}
}