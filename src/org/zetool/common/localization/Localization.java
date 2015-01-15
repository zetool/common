
package org.zetool.common.localization;

import java.util.List;
import java.util.Locale;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public interface Localization {

	/**
	 * Returns a localized string assigned to a key. The currently set prefix is
	 * added to the key.
	 * 
	 * @param key the key specifying the loaded string
	 * @return the localized string
	 */
	String getString( String key );

	/**
	 * Sets a prefix that is added to the key if {@link #getString(String)} is used.
	 * @param prefix the prefix that is added
	 */
	void setPrefix( String prefix );

	/**
	 * Returns a localized string assigned to a key. No prefix is added to the key.
	 * @param key the key specifying the loaded string
	 * @return the localized string
	 */
	String getStringWithoutPrefix( String key );

	void addSupportedLocale( Locale locale );

	List<Locale> getSupportedLocales();

	/**
	 * Clears any prefix.
	 * @see #setPrefix(java.lang.String)
	 */
	void clearPrefix();
	
}
