/**
 * SimpleFileHandler.java Created: 07.09.2012, 17:50:04
 */
package org.zetool.common.debug;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class SimpleFileHandler extends StreamHandler {

    private Level minLevel = Level.FINEST;  // by default, put out everything
    private Level maxLevel = Level.SEVERE;  // 
    private boolean append;
    private String fileName;
    private final Object fileLock = new Object();
    private String lockFileName;
    private FileOutputStream lockStream;
    private File file;

    /**
     * Construct a default <tt>SimpleFileHandler</tt>. This will be configured entirely from <tt>LogManager</tt>
     * properties (or their default values).
     * <p>
     *
     * @exception IOException if there are IO problems opening the files.
     * @exception SecurityException if a security manager exists and if the caller does not have
     * <tt>LoggingPermission("control"))</tt>.
     * @exception NullPointerException if pattern property is an empty String.
     */
    public SimpleFileHandler() throws IOException {
        super.setLevel(minLevel);
        openFile();
    }

    /**
     * Initialize a <tt>SimpleFileHandler</tt> to write to the given filename.
     *
     * @param fileName the name of the output file
     * @exception IOException if there are IO problems opening the files.
     * @exception SecurityException if a security manager exists and if the caller does not have
     * <tt>LoggingPermission("control")</tt>.
     * @exception IllegalArgumentException if pattern is an empty string
     */
    public SimpleFileHandler(String fileName) throws IOException {
        if (fileName.length() < 1) {
            throw new IllegalArgumentException();
        }
        this.fileName = fileName;
        openFile();
    }

    /**
     * Initialize a <tt>SimpleFileHandler</tt> to write to the given filename, with optional append.
     *
     * @param fileName the name of the output file
     * @param append specifies append mode
     * @exception IOException if there are IO problems opening the files.
     * @exception SecurityException if a security manager exists and if the caller does not have
     * <tt>LoggingPermission("control")</tt>.
     * @exception IllegalArgumentException if pattern is an empty string
     */
    public SimpleFileHandler(String fileName, boolean append) throws IOException {
        if (fileName.length() < 1) {
            throw new IllegalArgumentException();
        }
        this.fileName = fileName;
        this.append = append;
        openFile();
    }

    public Level getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Level minLevel) {
        this.minLevel = minLevel;
    }

    public Level getMaxLevel() {
        return maxLevel;
    }

    public void setErrLevel(Level errLevel) {
        this.maxLevel = errLevel;
    }

    private void open(File fname, boolean append) throws IOException {
        FileOutputStream fout = new FileOutputStream(fname.toString(), append);
        BufferedOutputStream bout = new BufferedOutputStream(fout);
        setOutputStream(bout);
    }

    /**
     * Private method to open the set of output files, based on the configured instance variables.
     */
    private void openFile() throws IOException {
        LogManager manager = LogManager.getLogManager();
        manager.checkAccess();

        // Create a lock file.  This grants us exclusive access
        // to our set of output files, as long as we are alive.
        for (;;) {
            // Generate a lock file name from the "unique" int.
            lockFileName = fileName + ".lck";
            // Now try to lock that filename.
            // Because some systems (e.g., Solaris) can only do file locks
            // between processes (and not within a process), we first check
            // if we ourself already have the file locked.
            synchronized (fileLock) {
                FileChannel fc;
                lockStream = new FileOutputStream(lockFileName);
                fc = lockStream.getChannel();
                boolean available;
                try {
                    available = fc.tryLock() != null;
                    // We got the lock OK.
                } catch (IOException ex) {
                    // We got an IOException while trying to get the lock.
                    // This normally indicates that locking is not supported
                    // on the target directory.  We have to proceed without
                    // getting a lock.   Drop through.
                    Logger.getLogger(SimpleFileHandler.class.getName()).log(Level.SEVERE, null, ex);
                    available = true;
                }
                if (available) { // stop, if locking worked
                    break;
                }

                fc.close();
                throw new IllegalStateException("Lock not possible");
            }
        }

        // Create and/or open the initial log file.
        file = new File(fileName);
        open(file, append);
    }

    /**
     * Format and publish a <tt>LogRecord</tt>.
     *
     * @param record description of the log event. A null record is silently ignored and is not published
     */
    @Override
    public synchronized void publish(LogRecord record) {
        if (!isLoggable(record)) {
            return;
        }
        super.publish(record);
        flush();
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return !(record.getLevel().intValue() < minLevel.intValue() || record.getLevel() == Level.OFF || record.getLevel().intValue() > maxLevel.intValue());
    }

    /**
     * Close all the file.
     *
     * @exception SecurityException if a security manager exists and if the caller does not have
     * <tt>LoggingPermission("control")</tt>.
     */
    @Override
    public synchronized void close() {
        super.close();
        // Unlock the lock file.
        if (lockFileName == null) {
            return;
        }
        try {
            // Closing the lock file's FileOutputStream will close
            // the underlying channel and free any locks.
            lockStream.close();
        } catch (Exception ex) {
            // some problems. cannot handle that
            Logger.getLogger(SimpleFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        new File(lockFileName).delete();
        lockFileName = null;
        lockStream = null;
    }
}
