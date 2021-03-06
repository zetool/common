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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.zetool.common.localization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@code Loalization} is a class that provides the ability to localize an application. It supports access to files
 * containing localized strings and number format conventions.
 *
 * @author Jan-Philipp Kappmeier
 */
public abstract class AbstractLocalization implements Localization {

    /** The resource bundle that is used by this localization instance. */
    private final String bundleName;
    /** The resource bundle that is selected (containing the localized strings). */
    private ResourceBundle bundle;
    /** A stack of prefixes that is added to the keys for localized strings. */
    private final Deque<String> prefix = new LinkedList<>();
    /** Indicates if only the key is returned, if an unknown key was used. Otherwise some larger text is returned. */
    private boolean returnKeyOnly = true;

    private final List<Locale> supportedLocales = new ArrayList<>();

    /**
     * Creates a new instance of the singleton and initializes with the default locale of the system.
     *
     * @param bundleName the name of the ressource bundle for the localization
     * @throws MissingResourceException if no resource bundle for the default system locale is found
     */
    AbstractLocalization(String bundleName, Locale currentLocale) {
        this.bundleName = bundleName;
        bundle = ResourceBundle.getBundle(bundleName, currentLocale);
    }

    @Override
    public void addSupportedLocale(Locale locale) {
        supportedLocales.add(locale);
    }

    @Override
    public List<Locale> getSupportedLocales() {
        return Collections.unmodifiableList(supportedLocales);
    }

    /**
     * Returns a localized string assigned to a key. The currently set prefix is added to the key.
     *
     * @param key the key specifying the loaded string
     * @return the localized string
     */
    @Override
    public final String getString(String key) {
        try {
            return key.isEmpty() ? "" : bundle.getString(currentPrefix() + key);
        } catch (MissingResourceException ex) {
            String msg = "Unknown Language key: '" + currentPrefix() + key + "'";
            Logger.getGlobal().log(Level.FINEST, msg, ex);
            return returnKeyOnly ? currentPrefix() + key : msg;
        }
    }

    /**
     * Returns a localized string assigned to a key. No prefix is added to the key.
     *
     * @param key the key specifying the loaded string
     * @return the localized string
     */
    @Override
    public final String getStringWithoutPrefix(String key) {
        try {
            return key.isEmpty() ? "" : bundle.getString(key);
        } catch (MissingResourceException ex) {
            String msg = "Unknown Language key: '" + key + "'";
            Logger.getGlobal().log(Level.FINEST, msg, ex);
            return returnKeyOnly ? key : msg;
        }
    }

    /**
     * Sets a prefix that is added to the key if {@link #getString(String)} is used.
     *
     * @param prefix the prefix that is added
     */
    @Override
    public final void setPrefix(String prefix) {
        this.prefix.push(prefix);
    }

    /**
     * Clears any prefix.
     *
     * @see #setPrefix(java.lang.String)
     */
    @Override
    public final void clearPrefix() {
        if(prefix.isEmpty()) {
            throw new IllegalStateException("No prefix set");
        }
        prefix.pop();
    }

    void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle(bundleName, locale);
    }

    public boolean isReturnKeyOnly() {
        return returnKeyOnly;
    }

    public void setReturnKeyOnly(boolean returnKeyOnly) {
        this.returnKeyOnly = returnKeyOnly;
    }

    @Override
    public String toString() {
        return "Localization: " + bundleName;
    }

    private String currentPrefix() {
        return prefix.isEmpty() ? "" : prefix.peek();
    }
}
