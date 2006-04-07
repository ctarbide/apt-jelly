/**
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
package net.sf.jelly.apt.tags;

///CLOVER:OFF

import net.sf.jelly.apt.APTJellyTest;

import java.util.Properties;

/**
 * Runs all tests for the <code>IfHasAnnotationTag</code> class.
 * <p/>
 *
 * @author Ryan Heaton
 */
public class TestIfHasAnnotation extends APTJellyTest {

  /**
   * Tests if has annotation.
   */
  public void testSetAnnotation() throws Exception {
    runScript("testIfHasAnnotation.jelly");
    Properties results = readOutputAsProperties("testIfHasAnnotation.properties");
    assertNotNull("Class should have been visited.", results.getProperty("net.sf.jelly.apt.samplesource.Family"));
    assertEquals("anotherFamilyExample", results.getProperty("net.sf.jelly.apt.samplesource.Family.annotation.name"));
    assertEquals("ANOTHER_FAMILY", results.getProperty("net.sf.jelly.apt.samplesource.Family.annotation.value"));
    assertEquals("another example of a family", results.getProperty("net.sf.jelly.apt.samplesource.Family.annotation.description"));
    assertEquals("something", results.getProperty("net.sf.jelly.apt.samplesource.Family.annotation.somethingWithDefault"));
    assertEquals("something", results.getProperty("with.annotation.value.tag.no.default"));
    assertEquals("no element here", results.getProperty("with.annotation.value.tag.no.element"));
    assertEquals("nothing", results.getProperty("with.annotation.value.tag.with.default"));
    assertEquals("constant", results.getProperty("with.annotation.value.tag.constant.ref"));
  }

}