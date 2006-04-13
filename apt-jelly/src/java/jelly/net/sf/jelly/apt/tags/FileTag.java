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

import net.sf.jelly.apt.strategies.FileStrategy;

/**
 * Tag that pipes its output to a new file <i>relative to the output directory specified for APT</i>.
 *
 * @author Ryan Heaton
 */
public class FileTag extends APTJellyTag<FileStrategy> {

  public FileTag() {
    super(new FileStrategy());
  }

  /**
   * The name of the file.
   *
   * @param name The name of the file.
   */
  public void setName(String name) {
    strategy.setName(name);
  }

  /**
   * Package relative to which the file should be named, or the empty string if none.
   *
   * @param pkg Package relative to which the file should be named, or the empty string if none.
   */
  public void setPackage(String pkg) {
    strategy.setPackage(pkg);
  }

  /**
   * The name of the charset to use.
   *
   * @param charset The name of the charset to use.
   */
  public void setCharset(String charset) {
    strategy.setCharset(charset);
  }
}
