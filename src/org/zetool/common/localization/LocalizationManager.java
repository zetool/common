package org.zetool.common.localization;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class LocalizationManager {
	/** The currently set locale (the information about the country). */
	private static Locale currentLocale;
	/** A number formatter for floating point numbers. */
	private static NumberFormat nfFloat;
	/** A number formatter for integral numbers. */
	private static NumberFormat nfInteger;
	/** A number formatter for percent values. */
	private static NumberFormat nfPercent;
	/** The singleton instance. */
	private volatile static LocalizationManager singleton;
	/** Mapping of property files to localizations. */
	private final HashMap<String, AbstractLocalization> localization = new HashMap<>();

	private LocalizationManager() {
		currentLocale = Locale.getDefault();
		nfFloat = NumberFormat.getNumberInstance( currentLocale );
		nfInteger = NumberFormat.getIntegerInstance( currentLocale );
		nfPercent = NumberFormat.getPercentInstance( currentLocale );
	}

	public Localization getLocalization( String bundleName ) throws MissingResourceException {
		AbstractLocalization al = localization.get( bundleName );
		if( al == null ) {
			al = new AbstractLocalization( bundleName, currentLocale ) {
			};
			localization.put( bundleName, al );
		}
		return al;
	}

	/**
	 * Returns the singleton instance of the {@code LocalizationManager}.
	 *
	 * @return the singleton instance of the {@code LocalizationManager}
	 */
	public static LocalizationManager getManager() {
		// needed because once there is singleton available no need to acquire
		// monitor again & again as it is costly
		if( singleton == null )
			synchronized( LocalizationManager.class ) {
				// this is needed if two threads are waiting at the monitor at the
				// time when singleton was getting instantiated
				if( singleton == null )
					singleton = new LocalizationManager();
			}
		return singleton;
	}

	/**
	 * Loads a new localization resource file from hard disk and sets new
	 * localized number converters. The language is set by a
	 * {@link java.util.Locale} object.
	 *
	 * <p>
	 * The localization file has to be found in the localization folder and has
	 * the name zevacuate.properties with the language information respectively.
	 *
	 * @param locale the locale that should be used
	 * @throws java.util.MissingResourceException if the locale cannot be found
	 */
	public final void setLocale( Locale locale ) throws MissingResourceException {
		currentLocale = locale;
		nfFloat = NumberFormat.getNumberInstance( currentLocale );
		nfInteger = NumberFormat.getIntegerInstance( currentLocale );
		nfPercent = NumberFormat.getPercentInstance( currentLocale );
		for( AbstractLocalization loc : localization.values() )
			loc.setLocale( locale );
	}

	/**
	 * Returns the currently selected {@link java.util.Locale}, that allows to
	 * format and read localized numbers.
	 *
	 * @return The currently selected locale.
	 */
	public final Locale getLocale() {
		return currentLocale;
	}

	/**
	 * Returns a formatter to read and write system specific floating point
	 * numbers.
	 *
	 * @return a formatter to read and write system specific floating point
	 * numbers
	 */
	public final NumberFormat getFloatConverter() {
		return nfFloat;
	}

	/**
	 * Returns a formatter to read and write system specific integral numbers.
	 *
	 * @return a formatter to read and write system specific integral numbers
	 */
	public final NumberFormat getIntegerConverter() {
		return nfInteger;
	}

	/**
	 * Returns a formatter that reads and writes percent values in the current
	 * locale.
	 *
	 * @return a formatter that reads and writes percent values in the current
	 * locale
	 */
	public final NumberFormat getPercentConverter() {
		return nfPercent;
	}

}
