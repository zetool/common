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

package org.zetool.common.util;

import org.zetool.common.localization.LocalizationManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * A set of helper methods for input and output operations.
 *
 * @author Jan-Philipp Kappmeier
 */
public final class IOTools {

  /**
   * Default constructor for utility class.
   */
  private IOTools() {
  }

  /**
   * Creates a new filename of the type pathPrefix### where ### indicates an increasing number with
   * {@code digits} digits. The new created filename has a greatest number. Gaps in the numbering
   * are ignored.
   *
   * @param path the path
   * @param filePrefix the prefix of the files
   * @param digits the number of digits of the numbering
   * @return the new filename including the path, the prefix and the number
   * @throws java.lang.IllegalArgumentException if digits is less or equal to zero
   * @throws java.lang.IllegalStateException if there are too many files beginning with prefix for
   * the specified number of digits or if an error converting the digits occured.
   */
  public static String getNextFreeNumberedFilepath( String path, String filePrefix, int digits ) {
    return path + getNextFreeNumberedFilename( path, filePrefix, digits );
  }

  /**
   * Creates a new filename of the type pathPrefix### where ### indicates an increasing number with
   * {@code digits} digits. The new created filename has a greatest number. Gaps in the numbering
   * are ignored.
   *
   * @param path the path in which the file shall be created.
   * @param filePrefix the prefix of the files
   * @param digits the number of digits of the numbering
   * @return the new filename without the path, the prefix and the number
   * @throws java.lang.IllegalArgumentException if digits is less or equal to zero
   * @throws java.lang.IllegalStateException if there are too many files beginning with prefix for
   * the specified number of digits or if an error converting the digits occured.
   */
  public static String getNextFreeNumberedFilename( String path, String filePrefix, int digits ) {
    if( digits <= 0 ) {
      throw new IllegalArgumentException( "Negative amount of digits: " + digits );
    }
    File[] files = new File( path ).listFiles();
    int fileNumber = 1;
    if( files != null ) {
      for( File file : files ) {
        fileNumber = Math.max( fileNumber, fileNumber(file, digits, filePrefix ) + 1 );
      }
    }
    try {
      return filePrefix + Formatter.fillLeadingZeros( fileNumber, digits );
    } catch( IllegalArgumentException ex ) {
      throw new java.lang.IllegalStateException( "Too many files with number length " + digits, ex );
    }
  }
  
  private static int fileNumber( File file, int digits, String filePrefix ) {
    final int prefixLen = filePrefix.length();
    if( !file.isDirectory() && file.getName().length() >= prefixLen + digits ) {
      final String foundPrefix = file.getName().substring( 0, prefixLen );
      if( foundPrefix.equals( filePrefix ) ) {
        String foundNumberText = file.getName().substring( prefixLen, prefixLen + digits );
        try {
          return LocalizationManager.getManager().getIntegerConverter().parse( foundNumberText ).intValue();
        } catch( ParseException ex ) {
          return -1;
        }
      }
    }
    return -1;
  }

  /**
   * Splits a string up at spaces but ignores spaces that are in parts between quotes. This is the
   * normal behavior of command line interfaces.
   *
   * @param command the string to be splitted up
   * @return a {@link List} containing all parts of the command
   */
  public static List<String> parseCommandString( String command ) {
    List<String> ret = new LinkedList<>();
    int i = -1;
    String s = "";
    boolean quotes = false;
    while( ++i < command.length() ) {
      if( command.charAt( i ) == '"' ) {
        s = addElement( ret, s );
        quotes = !quotes;
      } else if( command.charAt( i ) == ' ' && !quotes ) {
        s = addElement( ret, s );
      } else {
        s += command.charAt( i );
      }
    }
    addElement( ret, s );
    return ret;
  }

  /**
   * Adds a {@code String} to a list of strings if it is not the empty string.
   *
   * @param list the list of strings
   * @param s the {@code String} that is added
   * @return the empty string
   */
  private static String addElement( List<String> list, String s ) {
    if( !s.isEmpty() ) {
      list.add( s );
    }
    return "";
  }

  public static void createBackup( File file ) throws IOException {
    if( file != null && !file.getPath().isEmpty() ) {
      String source = file.getPath();
      String dest = source.substring( 0, source.length() - 3 ) + "bak";
      copyFile( file, new File( dest ), 100, true );
    }
  }

  public static void copyFile( File src, File dest, int bufSize, boolean force ) throws IOException {
    if( dest.exists() ) {
      if( !force ) {
        throw new IOException( "Cannot overwrite existing file: " + dest.getName() );
      }
      dest.delete();
    }

    byte[] buffer = new byte[bufSize];
    try( InputStream in = new FileInputStream( src ); OutputStream out = new FileOutputStream( dest ) ) {
      while( true ) {
        final int read = in.read( buffer );
        if( read == -1 ) { //-1 means EOF
          break;
        }
        out.write( buffer, 0, read );
      }
    }
  }
}
