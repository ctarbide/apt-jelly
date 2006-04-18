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

import freemarker.template.*;
import net.sf.jelly.apt.decorations.JavaDoc;

/**
 * Freemarker object wrapper for the APT-Jelly output classes.
 *
 * @author Ryan Heaton
 */
public class APTJellyObjectWrapper extends DefaultObjectWrapper {

  public APTJellyObjectWrapper() {
    setNullModel(new SimpleScalar("null"));
  }

  @Override
  public TemplateModel wrap(Object obj) throws TemplateModelException {
    if (obj instanceof JavaDoc) {
      return new FreemarkerJavaDoc((JavaDoc) obj);
    }

    if (obj instanceof Boolean) {
      return new ScalarBooleanModel((Boolean) obj);
    }

    return super.wrap(obj);
  }

  private static class ScalarBooleanModel implements TemplateBooleanModel, TemplateScalarModel {

    private final boolean value;

    public ScalarBooleanModel(boolean value) {
      this.value = value;
    }

    // Inherited.
    public boolean getAsBoolean() throws TemplateModelException {
      return value;
    }

    // Inherited.
    public String getAsString() throws TemplateModelException {
      return String.valueOf(value);
    }
  }

}
