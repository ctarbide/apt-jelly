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

package net.sf.jelly.apt;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;

/**
 * The context for the annotation processing tool.  Established by the processor
 * @author Ryan Heaton
 */
public class Context {

  private static AnnotationProcessorEnvironment currentEnvironment;

  /**
   * Get the current annotation processor environment.
   *
   * @return The current annotation processor environment.
   */
  public static AnnotationProcessorEnvironment getCurrentEnvironment() {
    if (currentEnvironment == null) {
      throw new IllegalStateException("The APT context hasn't been established.");
    }

    return currentEnvironment;
  }

  /**
   * Establish the current annotation processor environment.
   *
   * @param env the current annotation processor environment.
   */
  static void setCurrentEnvironment(AnnotationProcessorEnvironment env) {
    currentEnvironment = env;
  }

}
