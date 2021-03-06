/*
 * Copyright 2006 Ryan Heaton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sf.jelly.apt;

import com.sun.mirror.apt.AnnotationProcessorFactory;
import static junit.framework.Assert.fail;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Ryan Heaton
 */
public class APTTestUtil {

  /**
   * Creates a temporary output directory.
   *
   * @return A temporary output directory.
   */
  public static File createOutputDir() throws IOException {
    File outputDir = File.createTempFile("apttest", "");
    outputDir.delete();
    outputDir.mkdirs();
    return outputDir;
  }

  /**
   * Get a list of all java files in the specified directory.
   *
   * @param dir The directory in which to scan for java files.
   * @return The list of all java files in the specified directory.
   */
  public static List<String> getAllJavaFiles(File dir) {
    ArrayList<String> sourceFiles = new ArrayList<String>();
    findJavaFiles(dir, sourceFiles);
    return sourceFiles;
  }

  /**
   * Invoke APT on the given factory, options and source files.
   *
   * @param apf         The factory.
   * @param aptOptions  The options.
   * @param sourceFiles The source files.
   */
  public static void invokeAPT(AnnotationProcessorFactory apf, List<String> aptOptions, List<String> sourceFiles) {
    ArrayList<String> args = new ArrayList<String>();
    if (aptOptions != null) {
      args.addAll(aptOptions);
    }

    if ((sourceFiles == null) || (sourceFiles.isEmpty())) {
      fail("No source files were specified on which to invoke APT.");
    }

    args.addAll(sourceFiles);

    int procCode = com.sun.tools.apt.Main.process(apf, args.toArray(new String[args.size()]));
    TestCase.assertTrue(procCode == 0);
  }

  /**
   * Recursively finds all the java files in the specified directory and adds them all to the given collection.
   *
   * @param dir       The directory.
   * @param filenames The collection.
   */
  private static void findJavaFiles(File dir, Collection<String> filenames) {
    File[] javaFiles = dir.listFiles(JAVA_FILTER);
    for (File javaFile : javaFiles) {
      filenames.add(javaFile.getAbsolutePath());
    }

    File[] dirs = dir.listFiles(DIR_FILTER);
    for (File dir1 : dirs) {
      findJavaFiles(dir1, filenames);
    }
  }

  private static FileFilter JAVA_FILTER = new FileFilter() {
    public boolean accept(File pathname) {
      return pathname.getName().endsWith(".java");
    }
  };

  private static FileFilter DIR_FILTER = new FileFilter() {
    public boolean accept(File pathname) {
      return pathname.isDirectory();
    }
  };
}
