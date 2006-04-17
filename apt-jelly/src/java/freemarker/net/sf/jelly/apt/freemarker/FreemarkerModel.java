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

import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.decorations.DeclarationDecorator;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;
import net.sf.jelly.apt.decorations.declaration.DecoratedAnnotationMirror;

import java.util.HashMap;

import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.declaration.AnnotationMirror;
import com.sun.mirror.type.TypeMirror;

/**
 * The freemarker data model.
 *
 * @author Ryan Heaton
 */
public class FreemarkerModel extends HashMap implements TemplateModel {

  private static ThreadLocal<FreemarkerModel> localSingleton = new ThreadLocal<FreemarkerModel>() {
    @Override
    protected FreemarkerModel initialValue() {
      return new FreemarkerModel();
    }
  };

  public static FreemarkerModel get() {
    return localSingleton.get();
  }

  private FreemarkerModel() {
  }

  //Inherited.
  public Object getVariable(String var) {
    return get(var);
  }

  //Inherited.
  public void setVariable(String var, Object data) {
    put(var, data);
  }

  @Override
  public Object put(Object key, Object value) {
    if (value instanceof Declaration) {
      value = DeclarationDecorator.decorate((Declaration) value);
    }
    else if (value instanceof TypeMirror) {
      value = TypeMirrorDecorator.decorate((TypeMirror) value);
    }
    else if ((value instanceof AnnotationMirror) && !(value instanceof DecoratedAnnotationMirror)) {
      //AnnotationMirror is neither a TypeMirror nor a Declaration...
      value = new DecoratedAnnotationMirror((AnnotationMirror) value);
    }

    return super.put(key, value);
  }

}
