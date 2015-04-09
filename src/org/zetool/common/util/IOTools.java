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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

/**
 * Class IOTools
 * Erstellt 20.09.2008, 17:51:39
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
 * @author Jan-Philipp Kappmeier
 */
public final class IOTools {
	/** Default constructor for utility class. */
	private IOTools() {}

	/**
	 * Creates a new filename of the type pathPrefix### where ### indicates an
	 * increasing number with {@code digits} digits. The new created filename
	 * has a greatest number. Gaps in the numbering are ignored.
	 * @param path the path
	 * @param filePrefix the prefix of the files
	 * @param digits the number of digits of the numbering
	 * @return the new filename including the path, the prefix and the number
	 * @throws java.lang.IllegalArgumentException if digits is less or equal to zero
	 * @throws java.lang.IllegalStateException if there are too many files
	 * beginning with prefix for the specified number of digits or if an error
	 * converting the digits occured.
	 */
	public static String getNextFreeNumberedFilepath( String path, String filePrefix, int digits )
					throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
		return path + getNextFreeNumberedFilename( path, filePrefix, digits );
	}

	/**
	 * Creates a new filename of the type pathPrefix### where ### indicates an
	 * increasing number with {@code digits} digits. The new created filename
	 * has a greatest number. Gaps in the numbering are ignored.
	 * @param path the path in which the file shall be created.
	 * @param filePrefix the prefix of the files
	 * @param digits the number of digits of the numbering
	 * @return the new filename without the path, the prefix and the number
	 * @throws java.lang.IllegalArgumentException if digits is less or equal to zero
	 * @throws java.lang.IllegalStateException if there are too many files
	 * beginning with prefix for the specified number of digits or if an error
	 * converting the digits occured.
	 */
	public static String getNextFreeNumberedFilename( String path, String filePrefix, int digits )
			throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
		if( digits <= 0 )
			throw new IllegalArgumentException( "Digits must not be negative." );
		final int prefixLen = filePrefix.length();
		File[] files = new File( path ).listFiles();
		int lastIndex = 1;
		if (files != null) { for( File file : files )
				if( !file.isDirectory() && file.getName().length() >= prefixLen + digits ) {
					String foundPrefix = file.getName().substring( 0, prefixLen );
					if( foundPrefix.equals( filePrefix ) ) {
						String foundNumber = file.getName().substring( prefixLen, prefixLen + digits );
						int number;
						try {
							number = LocalizationManager.getManager().getIntegerConverter().parse( foundNumber ).intValue();
						} catch( ParseException ex ) {
							System.out.println( "Skipped file with same prefix: " + file.getName() );
							number = -1;
						}
						if( number >= lastIndex )
							lastIndex = number + 1;
					}
				}
		}
		try {
			return filePrefix + Formatter.fillLeadingZeros( lastIndex, digits );
		} catch( IllegalArgumentException ex ) {
			throw new java.lang.IllegalStateException( "Too many files with number length " + digits );
		}
	}


	/**
	 * Splits a string up at spaces but ignores spaces that are in parts between
	 * quotes. This is the normal behavior of command line interfaces.
	 * @param command the string to be splitted up
	 * @return a {@link List} containing all parts of the command
	 */
	public static List<String> parseCommandString( String command ) {
		LinkedList<String> ret = new LinkedList<>();
		int i = -1;
		String s = "";
		boolean quotes = false;
		while( ++i < command.length() ) {
			if( command.charAt( i ) == '"' ) {
				s = addElement( ret, s );
				quotes = !quotes;
			} else if( command.charAt( i ) == ' ' && !quotes)
				s = addElement( ret, s );
			else s += command.charAt( i );
		}
		addElement( ret, s );
		return ret;
	}

	/**
	 * Adds a {@code String} to a list of strings if it is not the empty string.
	 * @param list the list of strings
	 * @param s the {@code String} that is added
	 * @return the empty string
	 */
	private static String addElement( List<String> list, String s ) {
		if( !s.isEmpty(  ) )
			list.add( s );
		return "";
	}

	public static void createBackup( File file ) throws IOException {
		if( file != null && !file.getPath().isEmpty(  ) ) {
			String source = file.getPath();
			String dest = source.substring( 0, source.length() - 3 ) + "bak";
				copyFile( file, new File( dest ), 100, true );
		}
	}

	public static void copyFile( File src, File dest, int bufSize, boolean force ) throws IOException {
		if( dest.exists() ) {
			if( !force )
				throw new IOException( "Cannot overwrite existing file: " + dest.getName() );
			dest.delete();
		}
		byte[] buffer = new byte[bufSize];
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream( src );
			out = new FileOutputStream( dest );
			while( true ) {
				int read = in.read( buffer );
				if( read == -1 )
					//-1 bedeutet EOF
					break;
				out.write( buffer, 0, read );
			}
		} finally {
			// Sicherstellen, dass die Streams auch
			// bei einem throw geschlossen werden.
			// Falls in null ist, ist out auch null!
			if( in != null )
				//Falls tatsächlich in.close() und out.close()
				//Exceptions werfen, diejenige von 'out' geworfen wird.
				try {
					in.close();
				} finally {
					if( out != null )
						out.close();
				}
		}
	}
}