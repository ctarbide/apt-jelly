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
package net.sf.jelly.apt.decorations.declaration;

import com.sun.mirror.declaration.AnnotationMirror;
import com.sun.mirror.declaration.AnnotationTypeElementDeclaration;
import com.sun.mirror.declaration.AnnotationValue;
import com.sun.mirror.type.AnnotationType;
import com.sun.mirror.util.SourcePosition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A decorated annotation mirror provides:
 *
 * <ul>
 *   <li>properties for each of its elements
 * </ul>
 *
 * @author Ryan Heaton
 */
public class DecoratedAnnotationMirror extends HashMap<String, Object> implements AnnotationMirror {

  private AnnotationMirror delegate;

  public DecoratedAnnotationMirror(AnnotationMirror delegate) {
    if (delegate == null) {
      throw new IllegalArgumentException("A delegate must be provided.");
    }

    this.delegate = delegate;
    AnnotationType annotationType = delegate.getAnnotationType();
    Collection<AnnotationTypeElementDeclaration> allElements = annotationType.getDeclaration().getMethods();
    Map<AnnotationTypeElementDeclaration, AnnotationValue> elementValues = delegate.getElementValues();

    put("annotationType", annotationType);
    put("position", delegate.getPosition());
    put("elementValues", elementValues);
    for (AnnotationTypeElementDeclaration element : allElements) {
      if (elementValues.containsKey(element)) {
        put(element.getSimpleName(), elementValues.get(element).getValue());
      }
      else {
        put(element.getSimpleName(), element.getDefaultValue().getValue());
      }
    }
  }

  public AnnotationType getAnnotationType() {
    return this.delegate.getAnnotationType();
  }

  public SourcePosition getPosition() {
    return this.delegate.getPosition();
  }

  public Map<AnnotationTypeElementDeclaration, AnnotationValue> getElementValues() {
    return this.delegate.getElementValues();
  }

  public boolean equals(Object o) {
    return this.delegate.equals(o);
  }

}
