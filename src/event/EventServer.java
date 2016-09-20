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
package event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The event server allows for a certain event type to have some listeners who are notified when an event of the given
 * type occurs. When an {@link Event} is dispatched, also listeners of all supertypes are notified. The notification process takes care about
 * the notified listeners and makes sure that for each event each listener is only notified once.
 * 
 * The event server can be instantiated or used as a singleton.
 */
public class EventServer {
    /** The instance of the event server if used as a singleton. */
    private static EventServer instance;
    
    /** The mapping of registered {@link  Event} types to their respective {@link EventListener}s. */
    protected Map<Class<? extends Event>, List<EventListener<? extends Event>>> listeners;

    /**
     * Initializes the common instance of the singleton event server.
     */
    private EventServer() {
        listeners = new HashMap<>();
    }

    /**
     * Returns a static instance of the {@code EventServer} that can be used as a singleton.
     * 
     * @return a singleton instance of the {@code EventServer}
     */
    public static EventServer getInstance() {
        if (instance == null) {
            instance = new EventServer();
        }
        return instance;
    }

    /**
     * Registers a listener for a certain type of event.
     * 
     * @param <T> the {@link Event} type
     * @param listener the listener for events of the given type
     * @param eventType the class of the event
     */
    public <T extends Event> void registerListener(EventListener<? super T> listener, Class<T> eventType) {
        if (!listeners.containsKey(eventType)) {
            listeners.put(eventType, new LinkedList<>());
        }

        EventListener<? extends Event> typedListener = (EventListener<? extends Event>) listener;
        if (!listeners.get(eventType).contains(typedListener)) {
            listeners.get(eventType).add(typedListener);
        }
    }

    /**
     * Removes a listener from the list of known listeners for a certain event type. If the listener is not registered,
     * nothing happens.
     * 
     * @param <T> the event type
     * @param listener the listener to be removed
     * @param eventType the event type
     */
    public <T extends Event> void unregisterListener(EventListener<? super T> listener, Class<T> eventType) {
        if (!listeners.containsKey(eventType)) {
            return;
        }

        if (listeners.get(eventType).contains(listener)) {
            listeners.get(eventType).remove(listener);
        }
    }

    /**
     * Dispatches an event of some type to all listeners. During the dispatch process all subtypes of {@link Event}
     * that are satisfied by {@code <T>} are notified.
     * 
     * @param <T> the type of the event
     * @param event the event
     */
    public <T extends Event> void dispatchEvent(T event) {
        Class<? extends Event> eventType = event.getClass();
        Map<EventListener<? extends Event>, Boolean> notified = new HashMap<>();

        do {
            notifyListeners(event, eventType, notified);
            for (Class<?> cl : eventType.getInterfaces()) {
                if (Event.class.isAssignableFrom(cl)) {
                    notifyListeners(event, getSafe(cl), notified);
                }
            }
            
            eventType = computeSuperEventType(eventType);
        } while (eventType != null);
    }
    
    /**
     * Returns the super type of a given event type if it is still an {@link Event}, otherwise {@literal null}.
     * @param eventType the current event type
     * @return the super type which also is an {@link Event}, or {@literal null}
     */
    private Class<? extends Event> computeSuperEventType(Class<? extends Event> eventType) {
            Class<?> superType = eventType.getSuperclass();

            if (superType != null && Event.class.isAssignableFrom(superType)) {
                return getSafe(superType);
            }
            return null;
    }
    
    @SuppressWarnings("unchecked")
    private Class<? extends Event> getSafe(Class<?> superType) {
        return (Class<? extends Event>) superType;
    }

    private <T extends Event> void notifyListeners(T e, Class<? extends Event> eventType, Map<EventListener<? extends Event>, Boolean> notified) {
        if (listeners.containsKey(eventType)) {
            for (EventListener<? extends Event> listener : listeners.get(eventType)) {
                // Safe call here as the type is super of T and extends Event.
                if (notified.containsKey(listener) && notified.get(listener)) {
                    continue;
                }
                EventListener<? super T> typedListener = getListener(listener);
                typedListener.handleEvent(e);
                notified.put(listener, Boolean.TRUE);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> EventListener<? super T> getListener(EventListener<? extends Event> listener) {
        return (EventListener<? super T>)listener;
    }
}
