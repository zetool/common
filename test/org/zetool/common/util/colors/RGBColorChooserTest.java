package org.zetool.common.util.colors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Robot;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import javax.swing.SwingUtilities;

import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class RGBColorChooserTest {

    @Test
    public void onlyRrbPanel() {
        RGBColorChooser c = new RGBColorChooser(Color.yellow);

        assertThat(c.getChooserPanels().length, is(equalTo(1)));
    }

    @Test
    public void constructorTest() {
        Color initColor = Color.red;
        Callable<Color> runnable = () -> RGBColorChooser.showDialog(null, "RGB", initColor);

        StaticDialogSupplier<Color> shower = new StaticDialogSupplier<>(runnable, RGBColorChooser.DIALOG_NAME);
        shower.init();

        Dialog d = shower.getDialog();
        d.setVisible(false);

        Color retColor = shower.getResult();

        assertThat(retColor, is(equalTo(initColor)));

        disposeAllFrames();
    }

    public static void disposeAllFrames() {
        runInEventDispatchThread(() -> Frame.getFrames());
    }

    private static void dispose(Frame[] frames) {
        for (Frame frame : frames) {
            frame.dispose();
        }
    }

    /**
     * Executes a runnable in the event dispatch thread. This is just a convenient wrapper that
     * throws an{@link AssertionError} in case of failure.
     *
     * @param r the runnable to be executed
     */
    public static void runInEventDispatchThread(final Runnable r) {
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(r);
                createRobot().waitForIdle();
            } catch (InterruptedException | InvocationTargetException e) {
                throw new AssertionError("Failed to execute in event dispatch thread");
            }
        }
    }

    private static Robot createRobot() {
        Robot robot = null;
        try {
            robot = new Robot();
            robot.setAutoWaitForIdle(true);
            robot.setAutoDelay(5);
        } catch (AWTException e) {
            throw new AssertionError("Failed to create robot");
        }
        return robot;
    }

}
