/**
 * AlgorithmStatusEvent.java
 * Created: 19.03.2010, 10:55:01
 */
package org.zetool.common.algorithm;

import org.zetool.common.util.units.Quantity;
import org.zetool.common.util.units.TimeUnits;


/**
 * An algorithm event that is thrown by an algorithm if he wants to give notice
 * about its status with some additional text. No progress included.
 * @author Jan-Philipp Kappmeier
 */
public class AlgorithmStatusEvent extends AlgorithmEvent {

    /** An algorithm specific message. */
    private String message;

    /**
     * Creates an {@code AlgorithmStatusEvent} for the specified
     * algorithm and the current progress value.
     * @param algorithm the algorithm for which progress occurred.
		 * @param text the status text message
     */
    public AlgorithmStatusEvent(AbstractAlgorithm<?,?> algorithm, String text) {
				this( algorithm, new Quantity<>( System.currentTimeMillis(), TimeUnits.MilliSeconds ), text );
    }

    /**
     * Creates an {@code AlgorithmStatusEvent} for the specified algorithm
		 * and sets a status message.
     * @param algorithm the algorithm for which this status is defined
		 * @param eventTime the time (in milliseconds) when the event occured
     * @param text the status message
     */
    public AlgorithmStatusEvent(AbstractAlgorithm<?,?> algorithm, Quantity<TimeUnits> eventTime, String text) {
        super(algorithm, eventTime);
        this.message = text;
    }

    /**
     * Returns the message text assigned to this status event. The message is
		 * algorithm specific.
     * @return the status message
     */
    public String getMessage() {
        return message;
    }
}