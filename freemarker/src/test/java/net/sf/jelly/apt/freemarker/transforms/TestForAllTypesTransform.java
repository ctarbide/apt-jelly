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

/**
 * @author Ryan Heaton
 */
public class TestForAllTypesTransform extends FreemarkerTestCase {

  public void testForAllTypes() throws Exception {
    runScript("testForAllTypes.fmt");
    Properties results = readOutputAsProperties("testForAllTypes.properties");
    assertNotNull("Class should have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Family"));
    assertNotNull("Class should have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Person"));
    assertNull("Class should NOT have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Name"));
    assertNull("Class should NOT have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.NameType"));
    assertNull("Class should NOT have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Gender"));
    assertNull("Interface should NOT have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Identifiable"));
    assertEquals("personExample", results.getProperty("net.sf.jelly.apt.samplesource.Person.annotation.name"));
    assertEquals("PERSON", results.getProperty("net.sf.jelly.apt.samplesource.Person.annotation.value"));
    assertEquals("an example of a person", results.getProperty("net.sf.jelly.apt.samplesource.Person.annotation.description"));
  }

  public void testForAllTypesDifferentPackage() throws Exception {
    runScript("testForAllTypesDifferentPackage.fmt");
    Properties results = readOutputAsProperties("net/sf/jelly/apt/testForAllTypes.properties");
    assertNotNull("Class should have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Family"));
    assertNotNull("Class should have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Person"));
    assertNull("Class should NOT have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Name"));
    assertNull("Class should NOT have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.NameType"));
    assertNull("Class should NOT have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Gender"));
    assertNull("Interface should NOT have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Identifiable"));
    assertEquals("personExample", results.getProperty("net.sf.jelly.apt.samplesource.Person.annotation.name"));
    assertEquals("PERSON", results.getProperty("net.sf.jelly.apt.samplesource.Person.annotation.value"));
    assertEquals("an example of a person", results.getProperty("net.sf.jelly.apt.samplesource.Person.annotation.description"));
  }

  public void testForAllTypesIncludeInterfaces() throws Exception {
    runScript("testForAllTypesIncludeInterfaces.fmt");
    Properties results = readOutputAsProperties("testForAllTypesIncludeInterfaces.properties");
    assertNotNull("Class should have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Family"));
    assertNotNull("Class should have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Person"));
    assertNotNull("Interface should have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Identifiable"));
    assertNull("Class should NOT have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Name"));
    assertNull("Class should NOT have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.NameType"));
    assertNull("Class should NOT have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Gender"));
    assertEquals("IDable", results.getProperty("net.sf.jelly.apt.samplesource.Identifiable.annotation.name"));
    assertEquals("identifiable", results.getProperty("net.sf.jelly.apt.samplesource.Identifiable.annotation.value"));
    assertEquals("an identifiable object.", results.getProperty("net.sf.jelly.apt.samplesource.Identifiable.annotation.description"));
  }

  public void testForAllTypesUnspecifiedAnnotation() throws Exception {
    runScript("testForAllTypesUnspecifiedAnnotation.fmt");
    Properties results = readOutputAsProperties("testForAllTypesUnspecifiedAnnotation.properties");
    assertNotNull("Class should have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Family"));
    assertNotNull("Class should have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Person"));
    assertNotNull("Class should have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Name"));
    assertNotNull("Class should have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.NameType"));
    assertNotNull("Class should have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Gender"));
  }
}
