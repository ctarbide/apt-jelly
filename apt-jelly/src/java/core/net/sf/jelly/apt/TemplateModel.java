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

/**
 * Interface for the data model for the template engine.
 *
 * @author Ryan Heaton
 */
public interface TemplateModel {

  /**
   * Put data into the model under a specified variable name.
   *
   * @param var The variable name.
   * @param data The data to put in the model.
   */
  void setVariable(String var, Object data);
}
