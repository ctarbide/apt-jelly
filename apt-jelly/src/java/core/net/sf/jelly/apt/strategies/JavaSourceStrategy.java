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

package net.sf.jelly.apt.strategies;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import net.sf.jelly.apt.Context;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Strategy for getting the output to a new java source file.
 *
 * @author Ryan Heaton
 */
public class JavaSourceStrategy extends TemplateOutputRedirectionStrategy {

  private String name;

  /**
   * Get the writer for the specified java source file.
   *
   * @return the writer for the specified java source file.
   */
  public PrintWriter getWriter() throws MissingParameterException, IOException {
    if (name == null) {
      throw new MissingParameterException("name");
    }

    AnnotationProcessorEnvironment env = getAnnotationProcessorEnvironment();
    return env.getFiler().createSourceFile(name);
  }

  /**
   * The current annotation processor environment.
   *
   * @return The current annotation processor environment.
   */
  protected AnnotationProcessorEnvironment getAnnotationProcessorEnvironment() {
    return Context.getCurrentEnvironment();
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
