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

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModelException;
import net.sf.jelly.apt.TemplateModel;

/**
 * The freemarker data model.
 *
 * @author Ryan Heaton
 */
public class FreemarkerModel extends SimpleHash implements TemplateModel {

  private static ThreadLocal<FreemarkerModel> localSingleton = new ThreadLocal<FreemarkerModel>();

  /**
   * Set the current model.
   *
   * @param model The model to set.
   */
  public static void set(FreemarkerModel model) {
    localSingleton.set(model);
  }

  /**
   * Get the current model in the thread.
   *
   * @return the current model in the thread.
   */
  public static FreemarkerModel get() {
    return localSingleton.get();
  }

  // Inherited.
  public Object getVariable(String var) {
    try {
      return unwrap(get(var));
    }
    catch (TemplateModelException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Unwrap a model.
   *
   * @param model The model to unwrap.
   * @return The unwrapped model.
   */
  private Object unwrap(freemarker.template.TemplateModel model) throws TemplateModelException {
    return BeansWrapper.getDefaultInstance().unwrap(model);
  }

  // Inherited.
  public void setVariable(String var, Object data) {
    put(var, data);
  }

}
