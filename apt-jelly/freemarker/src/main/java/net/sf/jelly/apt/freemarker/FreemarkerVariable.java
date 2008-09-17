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

package net.sf.jelly.apt.freemarker;

/**
 * A variable in the freemarker model.
 *
 * @author Ryan Heaton
 */
public class FreemarkerVariable {

  private String name;
  private String namespace;
  private Object value;

  /**
   * The variable.
   *
   * @param namespace The namespace.
   * @param name The name.
   * @param value The value.
   */
  public FreemarkerVariable(String namespace, String name, Object value) {
    this.name = name;
    this.namespace = namespace;
    this.value = value;
  }

  /**
   * The name of the variable.
   *
   * @return The name of the variable.
   */
  public String getName() {
    return name;
  }

  /**
   * The namespace of the variable. <code>null</code> or <code>""</code> means the root namespace.
   *
   * @return The namespace of the variable.
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * The value of the variable.
   *
   * @return The value of the variable.
   */
  public Object getValue() {
    return value;
  }

}
