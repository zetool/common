/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
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
package org.zetool.common.datastructure;

import java.util.AbstractMap.SimpleEntry;

/**
 * Simple Key Value Pair that can be sorted by the keys.
 *
 * @param <K> The key type
 * @param <V> The value type
 * @author Jan-Philipp Kappmeier
 */
public class KeyValuePair<K extends Comparable<K>, V> extends SimpleEntry<K, V> implements Comparable<KeyValuePair<K, V>> {

    private static final long serialVersionUID = 1L;

    /**
     * @param key key to be set
     * @param value value to be set
     */
    public KeyValuePair(final K key, final V value) {
        super(key, value);
    }

    /**
     * Compares two Key Value Pairs according to the keys.
     *
     * @param o the other {@code KeyValuePair} with which this object is compared
     * @return the result of the comparison between {@code this.getKey()} and {o.getKey()}
     */
    @Override
    public int compareTo(KeyValuePair<K, V> o) {
        return this.getKey().compareTo(o.getKey());
    }
}
