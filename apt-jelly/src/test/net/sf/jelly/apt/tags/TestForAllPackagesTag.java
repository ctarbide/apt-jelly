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

import java.util.Properties;

/**
 * Runs all tests for the <code>ForAllPackagesTag</code> class.
 * <p/>
 *
 * @author Ryan Heaton
 */
public class TestForAllPackagesTag extends APTTest {

  public void testForAllPackages() throws Exception {
    runScript("testForAllPackages.jelly");
    Properties results = readOutputAsProperties("testForAllPackages.properties");
    assertEquals(3, results.size());
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.services"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource.annotations"));
    assertNotNull(results.getProperty("net.sf.jelly.apt.samplesource"));
  }

}