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

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import static net.sf.jelly.apt.APTTestUtil.getAllJavaFiles;
import static net.sf.jelly.apt.APTTestUtil.invokeAPT;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * @author Ryan Heaton
 */
public class InAPTTestCase extends TestCase {

  private static boolean IN_APT = false;

  /**
   * Runs itself within APT.
   */
  @Override
  public final void runBare() throws Throwable {
    boolean wrapInAPT = !IN_APT;

    if (wrapInAPT) {
      final InAPTTestCase testCase = this;
      APFInternal processorFactory = new APFInternal() {
        protected void processInternal() throws Throwable {
          testCase.setUp();
          try {
            testCase.runTest();
          }
          finally {
            testCase.tearDown();
          }
        }
      };

      invokeAPT(processorFactory, getAptOptions(), getAllJavaFiles(getContractTestDir()));
      processorFactory.throwPossibleThrowable();
    }
    else {
      super.runBare();
    }
  }

  protected static ArrayList<String> getAptOptions() {
    ArrayList<String> aptOpts = new ArrayList<String>();
    aptOpts.add("-cp");
    String classpath = System.getProperty("apt.core.test.class.path");
    if (classpath == null) {
      classpath = System.getProperty("java.class.path");
    }
    aptOpts.add(classpath);
    aptOpts.add("-nocompile");
    return aptOpts;
  }

  private static File getContractTestDir() {
    String subdir = System.getProperty("apt.core.test.src.dir");
    if (subdir == null) {
      throw new RuntimeException("A 'apt.core.test.src.dir' property must be defined.");
    }
    return new File(subdir);
  }

  /**
   * Gets the declaration given the fully-qualified name.  Asserts it exists.
   *
   * @param fqn The fqn.
   * @return The declaration.
   */
  protected TypeDeclaration getDeclaration(String fqn) {
    TypeDeclaration declaration = Context.getCurrentEnvironment().getTypeDeclaration(fqn);
    assertNotNull("No source def found: " + fqn, declaration);
    return declaration;
  }

  /**
   * Utility method for creating a test suite for the specified class.
   *
   * @param clazz The class for which to create a test suite.
   * @return The test suite.
   */
  public static Test createSuite(Class<? extends InAPTTestCase> clazz) {
    final TestSuite testSuite = new TestSuite(clazz);
    return new Test() {
      public int countTestCases() {
        return testSuite.countTestCases();
      }

      public void run(final TestResult result) {
        if (!IN_APT) {
          APFInternal processorFactory = new APFInternal() {
            protected void processInternal() {
              testSuite.run(result);
            }
          };

          invokeAPT(processorFactory, getAptOptions(), getAllJavaFiles(getContractTestDir()));
        }
        else {
          testSuite.run(result);
        }
      }
    };

  }

  protected static abstract class APFInternal extends ProcessorFactory implements AnnotationProcessor {

    private Throwable throwable = null;

    @Override
    public Collection<String> supportedOptions() {
      return Collections.emptyList();
    }

    @Override
    public Collection<String> supportedAnnotationTypes() {
      return Arrays.asList("*");
    }

    @Override
    public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> set, AnnotationProcessorEnvironment ape) {
      return super.getProcessorFor(set, ape);
    }

    @Override
    protected AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> annotations) {
      return this;
    }

    protected AnnotationProcessor newProcessor(URL url) {
      throw new UnsupportedOperationException();
    }

    public void process() {
      try {
        InAPTTestCase.IN_APT = true;
        processInternal();
        InAPTTestCase.IN_APT = false;
      }
      catch (Throwable throwable) {
        this.throwable = throwable;
      }
    }

    public void throwPossibleThrowable() throws Throwable {
      if (this.throwable != null) {
        throw this.throwable;
      }
    }

    protected abstract void processInternal() throws Throwable;

  }
}
