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

package net.sf.jelly.apt.freemarker.transforms;

import net.sf.jelly.apt.freemarker.FreemarkerTestCase;

import java.util.ArrayList;
import java.io.File;

/**
 * Runs all tests for the <code>JavaSourceTag</code> class.
 * <p/>
 *
 * @author Ryan Heaton
 */
public class TestJavaSourceTransform extends FreemarkerTestCase {

  /**
   * Tests generating a java source file.
   */
  public void testJavaSource() throws Exception {
    runScript("testJavaSource.fmt");
    ArrayList<String> foundfiles = new ArrayList<String>();
    findJavaFiles(getOutputDir(), foundfiles);
    assertEquals(1, foundfiles.size());

    File generatedFile = new File(getOutputDir(), "net/sf/jelly/apt/examples/ClassAndMethodPrinter.java");
    assertEquals(generatedFile.getAbsolutePath(), foundfiles.get(0));
    //todo: assert that the generated class compiles and/or does what it should?
  }

}
