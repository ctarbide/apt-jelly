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
import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.MissingAttributeException;
import org.apache.commons.jelly.XMLOutput;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Tag that pipes its output to a new java source file.
 *
 * @author Ryan Heaton
 */
public class JavaSourceTag extends APTTagSupport {

  private String name;

  public void doTag(XMLOutput output) throws MissingAttributeException, JellyTagException {
    if (name == null) {
      throw new MissingAttributeException("name");
    }

    PrintWriter redirect = getWriter();

    try {
      invokeBody(XMLOutput.createXMLOutput(redirect));
    }
    finally {
      redirect.close();
    }
  }

  /**
   * Get the writer for the specified java source file.
   *
   * @return the writer for the specified java source file.
   */
  protected PrintWriter getWriter() throws JellyTagException {
    AnnotationProcessorEnvironment env = getAnnotationProcessorEnvironment();
    try {
      return env.getFiler().createSourceFile(name);
    }
    catch (IOException e) {
      throw new JellyTagException(e);
    }
  }

  /**
   * Canonical (fully qualified) name of class whose source is to be written.
   *
   * @return Canonical (fully qualified) name of class whose source is to be written.
   */
  public String getName() {
    return name;
  }

  /**
   * Canonical (fully qualified) name of class whose source is to be written.
   *
   * @param name Canonical (fully qualified) name of class whose source is to be written.
   */
  public void setName(String name) {
    this.name = name;
  }

}
