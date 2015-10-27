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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class IOToolsNumberedFilesTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  @Rule
  public TemporaryFolder baseDirectory = new TemporaryFolder();
  private final String content = "testcontent";

  @Test
  public void testNextNumberedFirst() throws IOException {
    String filename = IOTools.getNextFreeNumberedFilename(baseDirectory.getRoot().getAbsolutePath(), "testfile", 3 );
    assertThat( filename, is( equalTo( "testfile001" ) ) );
  }

  @Test
  public void testNextNumberedFilenameNormal() throws IOException, InterruptedException {
    baseDirectory.newFile( "testfile001" ).createNewFile();
    baseDirectory.newFile( "testfile002" ).createNewFile();
    baseDirectory.newFile( "testfile003" ).createNewFile();
    String filename = IOTools.getNextFreeNumberedFilename(baseDirectory.getRoot().getAbsolutePath(), "testfile", 3 );
    assertThat( filename, is( equalTo( "testfile004" ) ) );
  }

  @Test
  public void testNextNumberedOneDigit() throws IOException {
    baseDirectory.newFile( "testfile1" ).createNewFile();
    baseDirectory.newFile( "testfile2" ).createNewFile();
    String filename = IOTools.getNextFreeNumberedFilename(baseDirectory.getRoot().getAbsolutePath(), "testfile", 1 );
    assertThat( filename, is( equalTo( "testfile3" ) ) );
  }

  @Test
  public void testNextNumberedSkip() throws IOException, InterruptedException {
    baseDirectory.newFile( "testfile003" ).createNewFile();
    String filename = IOTools.getNextFreeNumberedFilename(baseDirectory.getRoot().getAbsolutePath(), "testfile", 3 );
    assertThat( filename, is( equalTo( "testfile004" ) ) );
  }

  @Test
  public void testNextNumberedLimitExceeded() throws IOException, InterruptedException {
    for( int i = 1; i < 10; ++i ) {
      baseDirectory.newFile( "testfile" + i ).createNewFile();
    }
    exception.expect( IllegalStateException.class );
    IOTools.getNextFreeNumberedFilename(baseDirectory.getRoot().getAbsolutePath(), "testfile", 1 );
  }

  @Test
  public void testNextNumberedFileCompletePath() throws IOException, InterruptedException {
    baseDirectory.newFile( "testfile001" ).createNewFile();
    baseDirectory.newFile( "testfile002" ).createNewFile();
    baseDirectory.newFile( "testfile003" ).createNewFile();
    String filename = IOTools.getNextFreeNumberedFilepath(baseDirectory.getRoot().getAbsolutePath(), "testfile", 3 );
    assertThat(filename, is(equalTo(baseDirectory.getRoot().getAbsolutePath() + "testfile004" ) ) );
  }

  @Test
  public void testCopyFile() throws IOException {
    File sourceFile = baseDirectory.newFile( "sourcefile" );
    setupFile( sourceFile );

    IOTools.copyFile(sourceFile, new File( baseDirectory.getRoot(), "destinationfile" ), 1, false );

    final File checkFile = new File( baseDirectory.getRoot(), "destinationfile" );
    assertContent( checkFile );
  }

  @Test
  public void testCopyFileNoOverwrite() throws IOException {
    File sourceFile = baseDirectory.newFile( "sourcefile" );

    File destinationFile = baseDirectory.newFile( "destinationfile" );
    exception.expect( IOException.class );
    IOTools.copyFile( sourceFile, destinationFile, 1, false );
  }

  @Test
  public void testCopyFileNotChanged() throws IOException {
    File sourceFile = baseDirectory.newFile( "sourcefile" );

    File destinationFile = baseDirectory.newFile( "destinationfile" );
    try {
      IOTools.copyFile( sourceFile, destinationFile, 1, false );
    } catch( IOException ex ) {
      final File checkFile = new File( baseDirectory.getRoot(), "destinationfile" );
      assertThat( checkFile.exists(), is( true ) );
      assertThat( checkFile.length(), is( equalTo( (long) 0 ) ) );
      return;
    }
    fail();
  }

  @Test
  public void testCopyFileOverwrite() throws IOException {
    File sourceFile = baseDirectory.newFile( "sourcefile" );
    setupFile( sourceFile );

    baseDirectory.newFile( "destinationfile" );
    IOTools.copyFile(sourceFile, new File( baseDirectory.getRoot(), "destinationfile" ), 1, true );

    final File checkFile = new File( baseDirectory.getRoot(), "destinationfile" );
    assertContent( checkFile );
  }

  private void setupFile( File sourceFile ) throws IOException {
    FileWriter fw = new FileWriter( sourceFile );
    fw.write( content );
    fw.close();
  }

  private void assertContent( File checkFile ) throws IOException {
    assertThat( checkFile.length(), is( equalTo( (long) content.length() ) ) );
    final char[] cbuf = new char[content.length()];
    final FileReader fr = new FileReader( checkFile );
    fr.read( cbuf, 0, cbuf.length );
    fr.close();
    assertThat( content, is( equalTo( new String( cbuf ) ) ) );
  }
}
