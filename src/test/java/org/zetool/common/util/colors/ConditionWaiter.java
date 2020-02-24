package org.zetool.common.util.colors;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ConditionWaiter {

    private final BooleanSupplier s;
    private boolean finished;
    private final long period = 500;
    private final CountDownLatch done = new CountDownLatch(1);

    public ConditionWaiter(BooleanSupplier s) {
        this.s = s;
    }

    AtomicBoolean success = new AtomicBoolean(false);

    public boolean succeed() {
        return success.get();
    }

    public void waitForConditionUntil(final int timeout) {
        try {
            final Thread waiter = new Thread() {
                public void run() {
                    while (!finished) {
                        if (s.getAsBoolean()) {
                            success.set(true);
                            finished = true;
                        }
                        try {
                            Thread.sleep(period);
                        } catch (InterruptedException ex) {
                        }
                    }
                    done.countDown();
                }
            };
            waiter.start();
            done.await(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
        }
    }
}
