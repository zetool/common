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
package event;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestEvent {

    private static class HelpEvent implements Event {

        final String text;

        public HelpEvent(String text) {
            this.text = text;
        }
    }

    private static class SubEvent extends HelpEvent implements Event {

        public SubEvent(String text) {
            super(text);
        }

    }

    private static class HelpEventListener implements EventListener<HelpEvent> {

        int called = 0;
        String text = "";

        @Override
        public void handleEvent(HelpEvent event) {
            text = event.text;
            called++;
        }
    }

    @Test
    public void testEventServerDispatches() {
        EventServer es = EventServer.getInstance();
        HelpEventListener eventListener = new HelpEventListener();

        es.registerListener(eventListener, HelpEvent.class);

        es.dispatchEvent(new HelpEvent("some text"));
        assertThat(eventListener.called, is(equalTo(1)));
        assertThat(eventListener.text, is(equalTo("some text")));
    }

    @Test
    public void testEventServerDispatchesSubclasses() {
        EventServer es = EventServer.getInstance();
        HelpEventListener eventListener = new HelpEventListener();

        es.registerListener(eventListener, HelpEvent.class);

        es.dispatchEvent(new SubEvent("text"));
        assertThat(eventListener.called, is(equalTo(1)));
        assertThat(eventListener.text, is(equalTo("text")));
    }

    @Test
    public void testListenerNotifiedOnlyOnce() {
        EventServer es = EventServer.getInstance();
        HelpEventListener eventListener = new HelpEventListener();

        es.registerListener(eventListener, HelpEvent.class);
        es.registerListener(eventListener, SubEvent.class);

        es.dispatchEvent(new SubEvent("text"));
        assertThat(eventListener.called, is(equalTo(1)));
        assertThat(eventListener.text, is(equalTo("text")));
    }
}
