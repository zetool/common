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
 * This is a simple messaging event to submit status messages or error messages.
 *
 * @param <S>
 * @author Jan-Philipp Kappmeier
 */
public class MessageEvent<S> implements Event {

    public enum MessageType {

        Status,
        Error,
        MousePosition,
        EditMode,
        Log,
        LogError,
        VideoFrame;
    }
    protected final S source;
    private final String message;
    private final MessageType type;

    public MessageEvent(S source, MessageType type, String msg) {
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

    public MessageType getType() {
        return type;
    }
}
