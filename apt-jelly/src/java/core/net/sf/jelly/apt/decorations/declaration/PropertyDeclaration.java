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
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.type.TypeMirror;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A property, representing the getter/setter pair.  In all cases, the description of the property matches the description of the
 * getter, but the annotations are the union of the getter and the setter, with the intersection preferring the getter.
 *
 * @author Ryan Heaton
 */
public class PropertyDeclaration extends DecoratedMethodDeclaration {

  private final DecoratedMethodDeclaration setter;
  private final DecoratedMethodDeclaration getter;

  /**
   * A property declaration.
   *
   * @param getter The getter.
   * @param setter The setter.
   * @throws IllegalStateException If the getter and setter don't pair up.
   */
  public PropertyDeclaration(DecoratedMethodDeclaration getter, DecoratedMethodDeclaration setter) {
    super(getter);
    TypeMirror propertyType = getReturnType();

    this.getter = getter;
    this.setter = setter;
    if (setter != null) {
      Collection<ParameterDeclaration> parameters = setter.getParameters();
      if ((parameters == null) || (parameters.size() != 1) || (!propertyType.equals(parameters.iterator().next().getType()))) {
        throw new IllegalStateException(this.setter.getPosition() + ": invalid setter for " + propertyType);
      }
    }
  }

  /**
   * The type of this property.
   *
   * @return The type of this property.
   */
  public TypeMirror getPropertyType() {
    return getReturnType();
  }

  /**
   * The setter, or null if this property is a read-only property.
   *
   * @return The setter.
   */
  public DecoratedMethodDeclaration getSetter() {
    return setter;
  }

  /**
   * The getter.
   *
   * @return The getter.
   */
  public DecoratedMethodDeclaration getGetter() {
    return getter;
  }

  /**
   * Whether this property is read-only.
   *
   * @return Whether this property is read-only.
   */
  public boolean isReadOnly() {
    return getSetter() == null;
  }

  /**
   * Gets the annotations on the setter and the getter.  If the annotation is on both the setter and the getter, only the one on the getter will
   * be included.
   *
   * @return The union of the annotations on the getter and setter.
   */
  @Override
  public Map<String, AnnotationMirror> getAnnotations() {
    Map<String, AnnotationMirror> annotations = new HashMap<String, AnnotationMirror>();

    if (!isReadOnly()) {
      annotations.putAll(getSetter().getAnnotations());
    }

    annotations.putAll(getGetter().getAnnotations());

    return annotations;
  }

  /**
   * Gets the collection of annotations on the setter and the getter.  If the annotation is on both the setter and the getter, only the one on the getter will
   * be included.
   *
   * @return The union of the annotations on the getter and setter.
   */
  @Override
  public Collection<AnnotationMirror> getAnnotationMirrors() {
    return getAnnotations().values();
  }

  /**
   * Gets the annotation on the getter.  If it doesn't exist, returns the one on the setter.
   *
   * @param annotationType The annotation type.
   * @return The annotation.
   */
  @Override
  public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
    A annotation = super.getAnnotation(annotationType);

    if ((annotation == null) && (this.setter != null)) {
      annotation = this.setter.getAnnotation(annotationType);
    }

    return annotation;
  }

}
