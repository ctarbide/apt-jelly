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

import java.util.Properties;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Runs all tests for the <code>ForAllMethodsTag</code> class.
 * <p/>
 *
 * @author Ryan Heaton
 */
public class TestForAllMethodsTransform extends FreemarkerTestCase {

  /**
   * Demonstrates the ability to include superclasses when iterating over the methods of a class.
   */
  public void testIncludeSuperclasses() throws Exception {
    runScript("testIncludeSuperclasses.fmt");
    Properties results = readOutputAsProperties("testIncludeSuperclasses.properties");
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.ExtendedFamily.methods.getFather"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.ExtendedFamily.methods.getMother"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.ExtendedFamily.methods.setFather"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.ExtendedFamily.methods.setMother"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.ExtendedFamily.methods.getId"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.ExtendedFamily.methods.setId"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.ExtendedFamily.methods.getChildren"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.ExtendedFamily.methods.getPaternalGrandfather"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.ExtendedFamily.methods.getPaternalGrandmother"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.ExtendedFamily.methods.getMaternalGrandfather"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.ExtendedFamily.methods.getMaternalGrandmother"));

    FileInputStream outputStream = readOutputAsStream("testIncludeSuperclasses.properties");
    BufferedReader reader = new BufferedReader(new InputStreamReader(outputStream));
    String line = reader.readLine();
    boolean foundOnce = false;
    while (line != null) {
      boolean contains = line.contains("ExtendedFamily.methods.getId");
      assertFalse("The ExtendedFamily.getId method should only have been visited once, as it hides Family.getId", foundOnce && contains);
      foundOnce |= contains;
      line = reader.readLine();
    }
    assertTrue(foundOnce);
    reader.close();
    outputStream.close();
  }

  /**
   * Demonstrates the ability to include superinterfaces when iterating over the methods of a class.
   */
  public void testIncludeSuperinterfaces() throws Exception {
    runScript("testIncludeSuperinterfaces.fmt");
    Properties results = readOutputAsProperties("testIncludeSuperinterfaces.properties");
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.Nameable.methods.getId"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.Nameable.methods.getNames"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.Nameable.methods.setNames"));

    FileInputStream outputStream = readOutputAsStream("testIncludeSuperinterfaces.properties");
    BufferedReader reader = new BufferedReader(new InputStreamReader(outputStream));
    String line = reader.readLine();
    boolean foundOnce = false;
    while (line != null) {
      boolean contains = line.contains("Person.methods.getNames");
      assertFalse("The Person.getNames method should only have been visited once, as it hides Nameable.getNames", foundOnce && contains);
      foundOnce |= contains;
      line = reader.readLine();
    }
    assertTrue(foundOnce);
    reader.close();
    outputStream.close();
  }

  /**
   * Tests getter/setter/propertyName properties
   */
  public void testPropertyName() throws Exception {
    runScript("testPropertyName.fmt");
    Properties results = readOutputAsProperties("testPropertyName.properties");

    assertTrue(Boolean.valueOf(results.getProperty("net.sf.jelly.apt.samplesource.Person.methods.getNames.is.getter")));
    assertTrue(Boolean.valueOf(results.getProperty("net.sf.jelly.apt.samplesource.Person.methods.setNames.is.setter")));
    assertEquals("names", results.getProperty("net.sf.jelly.apt.samplesource.Person.methods.getNames.propertyName"));
    assertEquals("names", results.getProperty("net.sf.jelly.apt.samplesource.Person.methods.setNames.propertyName"));
  }

  protected void tearDown() throws Exception {
    //
  }

}
