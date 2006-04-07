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

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.Filer;
import org.apache.commons.jelly.JellyTagException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Tag that pipes its output to a new file <i>relative to the output directory specified for APT</i>.
 *
 * @author Ryan Heaton
 */
public class FileTag extends JavaSourceTag {

  private String pkg = "";
  private String charset;

  /**
   * Return the writer to the specified file.
   *
   * @return The writer to the specified file.
   */
  protected PrintWriter getWriter() throws JellyTagException {
    AnnotationProcessorEnvironment env = getAnnotationProcessorEnvironment();
    try {
      return env.getFiler().createTextFile(Filer.Location.SOURCE_TREE, pkg, new File(getName()), charset);
    }
    catch (IOException e) {
      throw new JellyTagException(e);
    }
  }

  /**
   * Package relative to which the file should be named, or the empty string if none.
   *
   * @return Package relative to which the file should be named, or the empty string if none.
   */
  public String getPackage() {
    return pkg;
  }

  /**
   * Package relative to which the file should be named, or the empty string if none.
   *
   * @param pkg Package relative to which the file should be named, or the empty string if none.
   */
  public void setPackage(String pkg) {
    this.pkg = pkg;
  }

  /**
   * The name of the charset to use.
   *
   * @return The name of the charset to use.
   */
  public String getCharset() {
    return charset;
  }

  /**
   * The name of the charset to use.
   *
   * @param charset The name of the charset to use.
   */
  public void setCharset(String charset) {
    this.charset = charset;
  }

}
