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

import freemarker.core.Environment;
import freemarker.template.*;
import net.sf.jelly.apt.decorations.JavaDoc;

import java.util.ArrayList;

/**
 * Freemarker template model for javadoc tags.
 *
 * @author Ryan Heaton
 */
public class FreemarkerJavaDoc implements TemplateHashModel, TemplateScalarModel {

  private final JavaDoc javaDoc;

  public FreemarkerJavaDoc(JavaDoc javaDoc) {
    this.javaDoc = javaDoc;
  }

  // Inherited.
  public TemplateModel get(String key) throws TemplateModelException {
    JavaDoc.JavaDocTagList values = javaDoc.get(key);
    return values != null ? new FreemarkerJavaDocTagList(values) : null;
  }

  // Inherited.
  public String getAsString() throws TemplateModelException {
    return toString();
  }

  // Inherited.
  public boolean isEmpty() throws TemplateModelException {
    return javaDoc.isEmpty();
  }

  // Inherited.
  @Override
  public String toString() {
    return javaDoc.toString();
  }

  public static class FreemarkerJavaDocTagList implements TemplateSequenceModel, TemplateScalarModel {

    private final ArrayList<String> values;

    /**
     * To construct a tag list, at least one value must be supplied.
     *
     * @param values The values in the tag list.
     */
    public FreemarkerJavaDocTagList(ArrayList<String> values) {
      this.values = values;
    }

    // Inherited.
    public int size() {
      return this.values.size();
    }

    // Inherited.
    public TemplateModel get(int index) throws TemplateModelException {
      return Environment.getCurrentEnvironment().getObjectWrapper().wrap(this.values.get(index));
    }

    // Inherited.
    public String getAsString() throws TemplateModelException {
      if (this.values.size() > 0) {
        return this.values.get(0);
      }

      return null;
    }
  }
}
