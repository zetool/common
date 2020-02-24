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

/**
 * A simple messaging event to submit status or error messages. The messages can have a source and also a generic type.
 * The type can be an arbitrary {@link Enum}. Due to the limitations of Java generics {@code MessageEvent}s with different types
 * can not be
 * distinguished by the type of the {@link EventServer}.
 *
 * @param <S> the source of the message
 * @param <T> the type of the message, an {@link Enum}
 * @author Jan-Philipp Kappmeier
 */
public class MessageEvent<S, T extends Enum<T>> implements Event {

    protected final S source;
    private final String message;
    private final T type;

    public MessageEvent(S source, T type, String msg) {
        this.source = source;
        this.message = msg;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public S getSource() {
        return source;
    }

    public T getType() {
        return type;
    }
}
