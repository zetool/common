package org.zetool.common.util.colors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class RGBColorChooserTest {
    private static final Color INIT_COLOR = Color.red;

    @Test
    public void onlyRrbPanel() {
        RGBColorChooser c = new RGBColorChooser(Color.yellow);

        assertThat(c.getChooserPanels().length, is(equalTo(1)));
    }

    @Test
    public void constructorTest() {
        Callable<Color> runnable = () -> RGBColorChooser.showDialog(null, "RGB", INIT_COLOR);

        StaticDialogSupplier<Color> shower = new StaticDialogSupplier<>(runnable, RGBColorChooser.DIALOG_NAME);
        shower.init();

        Dialog d = shower.getDialog();
        d.setVisible(false);

        Color retColor = shower.getResult();

        assertThat(retColor, is(equalTo(INIT_COLOR)));

        disposeAllFrames();
    }

    @Test
    public void selectedColorReturnedOnOK() {
        Color newColor = Color.green;
        Callable<Color> runnable = () -> RGBColorChooser.showDialog(null, "RGB", INIT_COLOR);

        StaticDialogSupplier<Color> shower = new StaticDialogSupplier<>(runnable, RGBColorChooser.DIALOG_NAME);
        shower.init();

        JDialog d = (JDialog)shower.getDialog();
        
        changeColorTo(d, newColor);
        d.getRootPane().getDefaultButton().doClick();
        
        Color retColor = shower.getResult();
        assertThat(retColor, is(equalTo(newColor)));

        disposeAllFrames();
    }
    
    private void changeColorTo(JDialog d, Color color) {
        for (Component c : d.getContentPane().getComponents()) {
            if (c instanceof JColorChooser) {
                JColorChooser ch = (JColorChooser) c;
                ch.setColor(color);
            }
        }
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
            } catch (InterruptedException | InvocationTargetException e) {
                throw new AssertionError("Failed to execute in event dispatch thread");
            }
        }
    }

}
