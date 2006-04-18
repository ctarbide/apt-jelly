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

package net.sf.jelly.apt.freemarker;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

import junit.framework.TestCase;

/**
 * @author Ryan Heaton
 */
public class FreemarkerTestCase extends TestCase {

  private File outputDir;

  protected void setUp() throws Exception {
    super.setUp();

    outputDir = File.createTempFile("apttest", "");
    outputDir.delete();
    outputDir.mkdirs();
  }

  public void runScript(String relativeResource) {
    URL resource = getClass().getResource(relativeResource);
    if (resource == null) {
      throw new IllegalStateException(String.format("Cannot load resource: %s",relativeResource));
    }
    runScript(resource);
  }

  public void runScript(URL url) {
    ArrayList<String> aptOpts = getAptOptions();

    String srcDirPath = System.getProperty("apt.test.src.dir");
    if (srcDirPath == null) {
      fail("The source directory for that apt tests must be specified in the 'apt.test.src.dir' property.");
    }

    File sourceDir = new File(srcDirPath);
    findJavaFiles(sourceDir, aptOpts);

    int procCode = com.sun.tools.apt.Main.process(new FreemarkerTestProcessorFactory(url), aptOpts.toArray(new String[aptOpts.size()]));
    assertTrue(procCode == 0);
  }

  protected ArrayList<String> getAptOptions() {
    ArrayList<String> aptOpts = new ArrayList<String>();
    aptOpts.add("-cp");
    aptOpts.add(System.getProperty("java.class.path"));
    aptOpts.add("-s");
    aptOpts.add(getOutputDir().getAbsolutePath());
    aptOpts.add("-nocompile");
    return aptOpts;
  }

  public File getOutputDir() {
    return outputDir;
  }

  protected void findJavaFiles(File dir, Collection<String> filenames) {
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

  protected Properties readOutputAsProperties(String output) throws IOException {
    FileInputStream inStream = readOutputAsStream(output);
    Properties results = new Properties();
    try {
      results.load(inStream);
    }
    finally {
      inStream.close();
    }
    return results;
  }

  protected FileInputStream readOutputAsStream(String output) throws FileNotFoundException {
    File outputFile = new File(getOutputDir(), output);
    assertTrue("No outputFile found.", outputFile.exists());
    return new FileInputStream(outputFile);
  }

  /**
   * An FreemarkerProcessorFactory that can be invoked more than once in the same JVM.
   */
  private class FreemarkerTestProcessorFactory extends FreemarkerProcessorFactory {

    public FreemarkerTestProcessorFactory(URL script) {
      super(script);
      round = 0; //reset the round.
    }

    @Override
    public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> set, AnnotationProcessorEnvironment env) {
      return super.getProcessorFor(set, env);
    }
  }

}
