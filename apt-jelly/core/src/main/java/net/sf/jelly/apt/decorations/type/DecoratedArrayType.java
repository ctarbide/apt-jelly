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
package net.sf.jelly.apt.decorations.type;

import com.sun.mirror.type.ArrayType;
import com.sun.mirror.type.TypeMirror;
import com.sun.mirror.util.TypeVisitor;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;

/**
 * @author Ryan Heaton
 */
public class DecoratedArrayType extends DecoratedReferenceType implements ArrayType {

  public DecoratedArrayType(ArrayType delegate) {
    super(delegate);
  }

  public TypeMirror getComponentType() {
    return TypeMirrorDecorator.decorate(((ArrayType)delegate).getComponentType());
  }

  public boolean isArray() {
    return true;
  }

  public void accept(TypeVisitor v) {
    v.visitArrayType(this);
  }

  @Override
  public boolean isInstanceOf(String className) {
    if (super.isInstanceOf(className)) {
      return true;
    }

    className = className.trim();
    if (className.endsWith("]")) {
      className = className.substring(0, className.length() - 1).trim();
      if (className.endsWith("[")) {
        className = className.substring(0, className.length() - 1).trim();
        DecoratedTypeMirror componentType = (DecoratedTypeMirror) getComponentType();
        return componentType.isInstanceOf(className);
      }
    }

    return false;
  }
}
