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

import net.sf.jelly.apt.APTJellyTest;

import java.util.ArrayList;
import java.util.Properties;

///CLOVER:OFF

/**
 * Runs a script as a basic example.
 * <p/>
 *
 * @author Ryan Heaton
 */
public class TestBasicExample extends APTJellyTest {

  public void testBasicExample() throws Exception {
    runScript("testBasicExample.jelly");
    Properties results = readOutputAsProperties("testBasicExample.properties");
//    results.store(System.out,"no comments");
    Properties assertedResults = new Properties();
    assertedResults.load(getClass().getResourceAsStream("basicExample.properties"));

//    assertEquals(assertedResults.size(), results.size());
    for (Object key : assertedResults.keySet()) {
      assertEquals(assertedResults.get(key), results.get(key));
    }
  }

  /**
   * referencing the apt options.
   */
  public void testAptOptions() throws Exception {
    runScript("testAptOptions.jelly");
    Properties results = readOutputAsProperties("testAptOptions.properties");
    assertEquals("my-option-value", results.getProperty("options.MyOption"));
  }

  @Override
  protected ArrayList<String> getAptOptions() {
    ArrayList<String> aptOptions = super.getAptOptions();
    aptOptions.add("-AMyOption=my-option-value");
    return aptOptions;
  }
}