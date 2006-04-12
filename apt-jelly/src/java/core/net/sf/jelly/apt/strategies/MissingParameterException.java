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

import net.sf.jelly.apt.TemplateException;

/**
 * Thrown if a parameter is missing for a strategy.
 * 
 * @author Ryan Heaton
 */
public class MissingParameterException extends TemplateException {

  private String parameter;

  /**
   * @param parameter The parameter that was missing.
   */
  public MissingParameterException(String parameter) {
    super("The '" + parameter + "' parameter must be specified.");
    this.parameter = parameter;
  }

  /**
   * @param message The message.
   * @param parameter The parameter that was missing.
   */
  public MissingParameterException(String message, String parameter) {
    super(message);
    this.parameter = parameter;
  }

  /**
   * The parameter that was missing.
   *
   * @return The parameter that was missing.
   */
  public String getParameter() {
    return parameter;
  }
}
