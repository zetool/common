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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.zetool.common.algorithm;

import org.zetool.common.util.units.Quantity;
import org.zetool.common.util.units.TimeUnits;

/**
 * An algorithm event that is thrown by an algorithm if he wants to give notice about its status with some additional
 * text. No progress included.
 *
 * @author Jan-Philipp Kappmeier
 */
public class AlgorithmStatusEvent extends AbstractAlgorithmEvent {

    /** An algorithm specific message. */
    private String message;

    /**
     * Creates an {@code AlgorithmStatusEvent} for the specified algorithm and the current progress value.
     *
     * @param algorithm the algorithm for which progress occurred.
     * @param text the status text message
     */
    public AlgorithmStatusEvent(AbstractAlgorithm<?, ?> algorithm, String text) {
        this(algorithm, new Quantity<>(System.currentTimeMillis(), TimeUnits.MilliSeconds), text);
    }

    /**
     * Creates an {@code AlgorithmStatusEvent} for the specified algorithm and sets a status message.
     *
     * @param algorithm the algorithm for which this status is defined
     * @param eventTime the time (in milliseconds) when the event occured
     * @param text the status message
     */
    public AlgorithmStatusEvent(AbstractAlgorithm<?, ?> algorithm, Quantity<TimeUnits> eventTime, String text) {
        super(algorithm, eventTime);
        this.message = text;
    }

    /**
     * Returns the message text assigned to this status event. The message is algorithm specific.
     *
     * @return the status message
     */
    public String getMessage() {
        return message;
    }
}
