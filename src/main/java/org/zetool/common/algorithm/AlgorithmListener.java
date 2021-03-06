/* zet evacuation tool copyright (c) 2007-14 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

/**
 * AlgorithmListener.java
 *
 */
package org.zetool.common.algorithm;

import java.util.EventListener;

/**
 * The interface for classes that want to receive events from algorithms.
 * @author Martin Groß
 */
@FunctionalInterface
public interface AlgorithmListener extends EventListener {

    /**
     * This method is called when an event occurred in an algorithm that is
     * being listened to.
     * @param event the event which occurred.
     */
    void eventOccurred(AbstractAlgorithmEvent event);
}
