/**
 * Debug.java Created: 22.07.2010 17:30:35
 */
package de.tu_berlin.coga.common.debug;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class Debug {
  /** The global logger used for debugging. */
  public final static Logger globalLogger = Logger.getGlobal();
  private static Level defaultLogLevel = Level.CONFIG;
  private static Handler handler = new SimpleSysoutHandler();

  static {
    setUpLogging();
    handler.setFormatter( new SimpleLogFormatter() );
    globalLogger.log( defaultLogLevel, "Logging set up to level {0}", defaultLogLevel );
  }

  /** Private utility class constructor. */
  private Debug() {
  }

  /**
   * Sets up the logging system in the following way: all loggers of the RootLogger and the global logger are removed.
   * Then, a {@link SimpleSysoutHandler} is added as handler to the default logger. The levels of both, the logger and
   * handler, are set to the default log level that is returned by {@link #getDefaultLogLevel() }.
   */
  public static void setUpLogging() {
    handler.setLevel( Debug.getDefaultLogLevel() );
    globalLogger.setLevel( getDefaultLogLevel() );

    for( Handler h : globalLogger.getParent().getHandlers() ) {
      globalLogger.getParent().removeHandler( h );
    }
    for( Handler h : globalLogger.getHandlers() ) {
      globalLogger.removeHandler( h );
    }

    globalLogger.addHandler( handler );
  }

  /**
   * @return the defaultLogLevel
   */
  public static Level getDefaultLogLevel() {
    return defaultLogLevel;
  }

  /**
   * Sets the log level for the global logger and the handler attached to it by {@link #setUpLogging() } to the given
   * value.
   *
   * @param defaultLogLevel the defaultLogLevel that is used
   */
  public static void setDefaultLogLevel( Level defaultLogLevel ) {
    Debug.defaultLogLevel = defaultLogLevel;
    handler.setLevel( defaultLogLevel );
    globalLogger.setLevel( defaultLogLevel );
    globalLogger.log( Level.OFF, "Logging set to level {0}", defaultLogLevel );
  }

  /**
   * A helper method that gives out an exception in a message window to the screen and also gives it out to the default
   * {@code err} stream.
   *
   * @param ex the exception
   */
  public static void printException( Throwable ex ) {
    globalLogger.severe( "Eine Exception trat auf:" );
    globalLogger.log( Level.SEVERE, "Message: {0}", ex.getMessage() );
    globalLogger.log( Level.SEVERE, "Localized: {0}", ex.getLocalizedMessage() );
    globalLogger.log( Level.SEVERE, "Cause: {0}", ex.getCause() );
    globalLogger.log( Level.SEVERE, null, ex );
  }

  /**
   * Shows a message dialog with the exception text.
   * @param ex the exception
   */
  public static void showExceptionMessage( Throwable ex ) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ex.printStackTrace( new PrintStream( bos ) );
    JOptionPane.showMessageDialog( null, bos.toString(), "Error", JOptionPane.ERROR_MESSAGE );
  }
}
