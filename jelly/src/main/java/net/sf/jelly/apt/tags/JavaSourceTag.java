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

import net.sf.jelly.apt.strategies.JavaSourceStrategy;
import net.sf.jelly.apt.APTJellyTag;

/**
 * Tag that pipes its output to a new java source file.
 *
 * @author Ryan Heaton
 */
public class JavaSourceTag extends APTJellyTag<JavaSourceStrategy> {

  public JavaSourceTag() {
    super(new JavaSourceStrategy());
  }

  /**
   * Canonical (fully qualified) name of class whose source is to be written.
   *
   * @param name Canonical (fully qualified) name of class whose source is to be written.
   */
  public void setName(String name) {
    strategy.setName(name);
  }

}
