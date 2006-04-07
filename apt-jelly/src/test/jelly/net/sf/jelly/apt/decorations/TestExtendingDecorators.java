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
package net.sf.jelly.apt.decorations;

import net.sf.jelly.apt.APTJellyTest;

import java.util.Properties;

/**
 * Tests the extension mechanism provided for the decorators.
 *
 * @author Ryan Heaton
 */
public class TestExtendingDecorators extends APTJellyTest {

  /**
   * Tests extending declarations.
   */
  public void testExtendingDeclarations() throws Exception {
    System.setProperty(DeclarationDecorator.class.getName(), HappyDeclarationDecorator.class.getName());

    runScript("testExtendingDecorations.jelly");
    Properties results = readOutputAsProperties("testExtendingDecorations.properties");
    assertEquals(2, results.size());
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.Family"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.services.FamilyService"));

    System.getProperties().remove(DeclarationDecorator.class.getName());
  }
}
