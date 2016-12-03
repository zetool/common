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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.zetool.common.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.zetool.common.algorithm.AbstractAlgorithm.State;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class AbstractAlgorithmTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static class FakeAlgorithm extends AbstractAlgorithm<Object, Object> {

        private boolean called = false;

        public FakeAlgorithm() {
        }

        public FakeAlgorithm(String name) {
            super(name);
        }

        @Override
        public Object runAlgorithm(Object problem) {
            called = true;
            return problem;
        }
    }

    @Test
    public void testSimple() {
        assertAlgorithm(new FakeAlgorithm(), "FakeAlgorithm");

        FakeAlgorithm t = new FakeAlgorithm("TestFakeAlgorithm");
        assertAlgorithm(t, "TestFakeAlgorithm");

        t.setName("New Algorithm Name");
        assertThat(t.getName(), is(equalTo("New Algorithm Name")));

        t.setDescription("Algorithm Description");
        assertThat(t.getDescription(), is(equalTo("Algorithm Description")));
    }

    @Test
    public void testAnonymous() {
        assertAlgorithm(new AbstractAlgorithm<Object, Object>() {
            @Override
            protected Object runAlgorithm(Object problem) {
                return null;
            }
        }, "AbstractAlgorithm");
    }

    static class AlgorithmEventCounter implements AlgorithmListener {

        public int started = 0;
        public int terminated = 0;
        public boolean terminatedAfterStarted = false;

        @Override
        public void eventOccurred(AbstractAlgorithmEvent event) {
            System.out.println("Event: " + event);
            if (event instanceof AlgorithmStartedEvent) {
                started++;
            }
            if (event instanceof AlgorithmTerminatedEvent) {
                terminated++;
                if (started > 0) {
                    terminatedAfterStarted = true;
                }
            }
        }
    };

    @Test(expected = IllegalStateException.class)
    public void testFailsWithoutInitialization() {
        Algorithm<?, ?> algorithm = new FakeAlgorithm();
        algorithm.call();
    }

    @Test
    public void testFiresEvents() {
        AlgorithmEventCounter listener = new AlgorithmEventCounter();
        AbstractAlgorithm<Object, Object> algorithm = new FakeAlgorithm();
        algorithm.setProblem(new Object());
        algorithm.addAlgorithmListener(listener);
        algorithm.run();

        assertThat(listener.started, is(equalTo(1)));
        assertThat(listener.terminated, is(equalTo(1)));
        assertThat(listener.terminatedAfterStarted, is(equalTo(true)));
    }

    private void assertAlgorithm(AbstractAlgorithm<?, ?> t, String name) {
        assertThat(t.getName(), is(equalTo(name)));
        assertThat(t.getParameterSet(), is(not(nullValue())));
        assertState(t, AbstractAlgorithm.State.UNINITIALIZED);
    }

    @Test
    public void testRun() {
        FakeAlgorithm t = new FakeAlgorithm();
        Object problem = new Object();

        assertState(t, AbstractAlgorithm.State.UNINITIALIZED);
        t.setProblem(problem);
        assertState(t, AbstractAlgorithm.State.WAITING);

        t.run();

        assertThat(t.getProblem(), is(sameInstance(problem)));
        assertThat(t.called, is(true));
        assertState(t, AbstractAlgorithm.State.SOLVED);
    }

    @Test
    public void testRunFails() {
        RuntimeException re = new RuntimeException();
        AbstractAlgorithm<Object, ?> a = new AbstractAlgorithm<Object, Object>() {

            @Override
            protected Object runAlgorithm(Object problem) {
                throw re;
            }
        };
        a.setProblem(new Object());
        a.run();

        assertState(a, State.SOLVING_FAILED);
        assertThat(a.getCause(), is(sameInstance(re)));
    }

    @Test
    public void testRunning() {
        final AtomicBoolean continueComputation = new AtomicBoolean(false);
        final AtomicBoolean executionStarted = new AtomicBoolean(false);
        AbstractAlgorithm<Object, ?> a = new AbstractAlgorithm<Object, Object>() {
            @Override
            protected Object runAlgorithm(Object problem) {
                executionStarted.set(true);
                while (!continueComputation.get()) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AbstractAlgorithmTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return null;
            }
        };
        a.setProblem(new Object());
        (new Thread(a)).start();
        while (!executionStarted.get()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(AbstractAlgorithmTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        assertState(a, State.SOLVING);
        continueComputation.set(true);
    }

    @Test
    public void testSolution() {
        FakeAlgorithm t = new FakeAlgorithm();
        Object problem = new Object();
        t.setProblem(problem);

        t.run();

        assertThat(t.getSolution(), is(sameInstance(problem)));

        t = new FakeAlgorithm();
        exception.expect(IllegalStateException.class);
        t.getSolution();
    }

    @Test
    public void testListener() {
        AbstractAlgorithm<Object, ?> t = new FakeAlgorithm();
        final List<AbstractAlgorithmEvent> events = new LinkedList<>();
        t.addAlgorithmListener((AbstractAlgorithmEvent event) -> events.add(event));
        t.setProblem(new Object());

        t.run();

        assertThat(events.get(0), instanceOf(AlgorithmStartedEvent.class));
        assertThat(events.get(events.size() - 1), instanceOf(AlgorithmTerminatedEvent.class));
    }

    @Test(expected = IllegalStateException.class)
    public void testAddListenerFails() {
        AbstractAlgorithm<Object, ?> t = new FakeAlgorithm();
        t.setProblem(new Object());
        t.run();

        t.addAlgorithmListener((AbstractAlgorithmEvent event) -> {
        });
    }

    @Test
    public void testRemoveListener() {
        AbstractAlgorithm<Object, ?> t = new FakeAlgorithm();
        t.removeAlgorithmListener((AbstractAlgorithmEvent event) -> {
        });
        final AtomicInteger i1 = new AtomicInteger();
        final AtomicInteger i2 = new AtomicInteger();
        AlgorithmListener al1 = (AbstractAlgorithmEvent event) -> i1.incrementAndGet();
        AlgorithmListener al2 = (AbstractAlgorithmEvent event) -> i2.incrementAndGet();
        t.addAlgorithmListener(al1);
        t.addAlgorithmListener(al2);
        t.removeAlgorithmListener(al2);
        t.setProblem(new Object());

        t.run();

        assertThat(i1.get(), is(greaterThan(0)));
        assertThat(i2.get(), is(equalTo(0)));
    }
    
    @Test
    public void testAddListenersFromOtherInstance() {
        AbstractAlgorithm<Object, Object> firstAlgorithm = new FakeAlgorithm();
        final AtomicInteger i1 = new AtomicInteger();
        final AtomicInteger i2 = new AtomicInteger();
        AlgorithmListener al1 = (AbstractAlgorithmEvent event) -> i1.incrementAndGet();
        AlgorithmListener al2 = (AbstractAlgorithmEvent event) -> i2.incrementAndGet();
        
        firstAlgorithm.addAlgorithmListener(al1);
        firstAlgorithm.addAlgorithmListener(al2);
        
        AbstractAlgorithm<Object,Object> secondAlgorithm = new FakeAlgorithm();
        secondAlgorithm.addAlgorithmListener(firstAlgorithm);
        firstAlgorithm.removeAlgorithmListener(al2);
        
        secondAlgorithm.setProblem(new Object());
        secondAlgorithm.run();
        
        assertState(firstAlgorithm, AbstractAlgorithm.State.UNINITIALIZED);
        assertState(secondAlgorithm, AbstractAlgorithm.State.SOLVED);
        assertThat(i1.get(), is(greaterThan(0)));
        assertThat(i2.get(), is(greaterThan(0)));
    }

    @Test
    public void testProgress() {
        AbstractAlgorithm<Object, ?> a = new AbstractAlgorithm<Object, Object>() {
            @Override
            protected Object runAlgorithm(Object problem) {
                fireProgressEvent(.25);
                fireProgressEvent(.5);
                fireProgressEvent(.75);
                fireProgressEvent(1);
                return null;
            }
        };
        final List<AbstractAlgorithmEvent> events = new LinkedList<>();
        a.addAlgorithmListener((AbstractAlgorithmEvent event) -> {
            if (event instanceof AlgorithmProgressEvent) {
                events.add(event);
            }
        });
        a.setAccuracy(.5);
        a.setProblem(new Object());

        a.run();

        assertThat(events.size(), is(equalTo(2)));
    }

    @Test
    public void testProgressFails() {
        final AtomicBoolean causeIsNull = new AtomicBoolean(false);
        final AtomicBoolean exceptionThrown = new AtomicBoolean(true);
        AbstractAlgorithm<Object, ?> a = new AbstractAlgorithm<Object, Object>() {
            @Override
            protected Object runAlgorithm(Object problem) {
                fireProgressEvent(.1);
                causeIsNull.set(getCause() == null);
                fireProgressEvent(.05);
                // only executed if throwing the exception fails
                exceptionThrown.set(false);
                return null;
            }
        };

        a.setAccuracy(.1);
        a.setProblem(new Object());
        a.run();

        assertThat(causeIsNull.get(), is(true));
        assertThat(exceptionThrown.get(), is(true));
        assertThat(a.getCause(), is(instanceOf(IllegalArgumentException.class)));
        assertThat(a.getState(), is(equalTo(State.SOLVING_FAILED)));
    }
    
    @Test
    public void testRangesWorks() {
        AbstractAlgorithm<?,?> mock = new FakeAlgorithm();
        mock.setAccuracy(0.3);
        assertThat(mock.getAccuracy(), is(closeTo(0.3, 10e-8)));
    }

    @Test
    public void testBoundaryRanges() {
        AbstractAlgorithm<?,?> mock = new FakeAlgorithm();
        mock.setAccuracy(0.0000001);
        assertThat(mock.getAccuracy(), is(closeTo(0, 10e-6)));
        mock.setAccuracy(1.0);
        assertThat(mock.getAccuracy(), is(closeTo(1, 10e-8)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeProgressFails() {
        AbstractAlgorithm<?,?> mock = new FakeAlgorithm();
        mock.setAccuracy(0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAccuracyLimitFails() {
        AbstractAlgorithm<?,?> mock = new FakeAlgorithm();
        mock.setAccuracy(1.0001);
    }
    
    @Test
    public void testIntegralAccuracy() {
        AbstractAlgorithm<?,?> mock = new FakeAlgorithm();
        mock.setAccuracy(2);
        assertThat(mock.getAccuracy(), is(closeTo(0.5, 10e-8)));
    }

    private void assertState(AbstractAlgorithm<?, ?> t, AbstractAlgorithm.State expectedState) {
        assertThat(t.getState(), is(equalTo(expectedState)));
        switch (expectedState) {
            case UNINITIALIZED:
                assertThat(t.isPaused(), is(false));
                assertThat(t.isProblemInitialized(), is(false));
                assertThat(t.isProblemSolved(), is(false));
                assertThat(t.isRunning(), is(false));
                break;
            case SOLVING:
                assertThat(t.isPaused(), is(false));
                assertThat(t.isProblemInitialized(), is(true));
                assertThat(t.isProblemSolved(), is(false));
                assertThat(t.isRunning(), is(true));
                break;
            case SOLVED:
                assertThat(t.isPaused(), is(false));
                assertThat(t.isProblemInitialized(), is(true));
                assertThat(t.isProblemSolved(), is(true));
                assertThat(t.isRunning(), is(false));
                break;
            case SOLVING_FAILED:
            case WAITING:
                assertThat(t.isPaused(), is(false));
                assertThat(t.isProblemInitialized(), is(true));
                assertThat(t.isProblemSolved(), is(false));
                assertThat(t.isRunning(), is(false));
                break;
            default:
                fail("Undefined state in test: " + expectedState);
        }
    }
}
