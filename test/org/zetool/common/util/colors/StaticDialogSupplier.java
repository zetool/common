package org.zetool.common.util.colors;

import static org.zetool.common.util.colors.RGBColorChooserTest.runInEventDispatchThread;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BooleanSupplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class StaticDialogSupplier<T> {

    FutureTask<T> dialogTask;
    private final String dialogName;
    T retColor;

    public static StaticDialogSupplier<Void> getFor(Runnable runnable, String dialogName) {
        return new StaticDialogSupplier<>(() -> {
            runnable.run();
            return null;
        }, dialogName);
    }

    public StaticDialogSupplier(Callable<T> callable, String dialogName) {
        dialogTask = new FutureTask<>(callable);
        this.dialogName = dialogName;
    }

    T getResult() {
        try {
            retColor = dialogTask.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException ex) {
            Logger.getLogger(StaticDialogSupplier.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError();
        }
        return retColor;
    }

    Callable c;

    public void init() {
        final Thread shower = new Thread() {
            {{
                    setDaemon(true);
            }}

            @Override
            public void run() {
                runInEventDispatchThread(dialogTask);
            }

        };
        shower.start();

        // Wait until doalog shows up
        BooleanSupplier s = new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                Dialog dialog = findNamedDialog(dialogName);
                return dialog != null;
            }
        };
        ConditionWaiter st = new ConditionWaiter(s);
        st.waitForConditionUntil(10);
        if (!st.succeed()) {
            throw new AssertionError("Not successful in waiting");
        }
    }

    public Dialog getDialog() {
        return findNamedDialog(dialogName);
    }

    /**
     * The first found dialog that has the given name and is showing (though the owning frame need
     * not be showing).
     */
    private static Dialog findNamedDialog(String name) {
        Frame[] allFrames = Frame.getFrames();
        for (Frame allFrame : allFrames) {
            Window[] subWindows = allFrame.getOwnedWindows();
            for (Window subWindow : subWindows) {
                if (subWindow instanceof Dialog) {
                    Dialog d = (Dialog) subWindow;

                    if (name.equals(d.getName()) && d.isShowing()) {
                        return (Dialog) subWindow;
                    }
                }
            }
        }
        return null;
    }

}
