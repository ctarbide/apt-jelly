/**
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
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.type.AnnotationType;
import com.sun.mirror.util.DeclarationVisitor;
import com.sun.mirror.util.SourcePosition;
import net.sf.jelly.apt.decorations.DeclarationDecorator;
import net.sf.jelly.apt.decorations.JavaDoc;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A decorated declaration provides:
 *
 * <ul>
 *   <li>boolean properties for each {@link Modifier}
 *   <li>A property for the javadoc for this declaration.
 * </ul>
 *
 * @author Ryan Heaton
 */
public class DecoratedDeclaration implements Declaration {

  protected final Declaration delegate;
  protected final JavaDoc javaDoc;

  public DecoratedDeclaration(Declaration delegate) {
    this.delegate = delegate;
    this.javaDoc = new JavaDoc(delegate.getDocComment());
  }

  /**
   * Whether the declaration is <code>public</code>.
   *
   * @return Whether the declaration is <code>public</code>.
   */
  public boolean isPublic() {
    return getModifiers().contains(Modifier.PUBLIC);
  }

  /**
   * Whether the declaration is <code>protected</code>.
   *
   * @return Whether the declaration is <code>protected</code>.
   */
  public boolean isProtected() {
    return getModifiers().contains(Modifier.PROTECTED);
  }

  /**
   * Whether the declaration is <code>private</code>.
   *
   * @return Whether the declaration is <code>private</code>.
   */
  public boolean isPrivate() {
    return getModifiers().contains(Modifier.PRIVATE);
  }

  /**
   * Whether the declaration is <code>abstract</code>.
   *
   * @return Whether the declaration is <code>abstract</code>.
   */
  public boolean isAbstract() {
    return getModifiers().contains(Modifier.ABSTRACT);
  }

  /**
   * Whether the declaration is <code>static</code>.
   *
   * @return Whether the declaration is <code>static</code>.
   */
  public boolean isStatic() {
    return getModifiers().contains(Modifier.STATIC);
  }

  /**
   * Whether the declaration is <code>final</code>.
   *
   * @return Whether the declaration is <code>final</code>.
   */
  public boolean isFinal() {
    return getModifiers().contains(Modifier.FINAL);
  }

  /**
   * Whether the declaration is <code>transient</code>.
   *
   * @return Whether the declaration is <code>transient</code>.
   */
  public boolean isTransient() {
    return getModifiers().contains(Modifier.TRANSIENT);
  }

  /**
   * Whether the declaration is <code>volatile</code>.
   *
   * @return Whether the declaration is <code>volatile</code>.
   */
  public boolean isVolatile() {
    return getModifiers().contains(Modifier.VOLATILE);
  }

  /**
   * Whether the declaration is <code>synchronized</code>.
   *
   * @return Whether the declaration is <code>synchronized</code>.
   */
  public boolean isSynchronized() {
    return getModifiers().contains(Modifier.SYNCHRONIZED);
  }

  /**
   * Whether the declaration is <code>native</code>.
   *
   * @return Whether the declaration is <code>native</code>.
   */
  public boolean isNative() {
    return getModifiers().contains(Modifier.NATIVE);
  }

  /**
   * Whether the declaration is <code>strictfp</code>.
   *
   * @return Whether the declaration is <code>strictfp</code>.
   */
  public boolean isStrictfp() {
    return getModifiers().contains(Modifier.STRICTFP);
  }

  /**
   * The javadoc for this declaration.
   *
   * @return The javadoc for this declaration.
   */
  public JavaDoc getJavaDoc() {
    return javaDoc;
  }

  /**
   * A map of annotations for this declaration.
   *
   * @return A map of annotations for this declaration.
   */
  public Map<String, AnnotationMirror> getAnnotations() {
    HashMap<String, AnnotationMirror> annotations = new HashMap<String, AnnotationMirror>();
    for (AnnotationMirror annotationMirror : getAnnotationMirrors()) {
      AnnotationType annotationType = annotationMirror.getAnnotationType();
      if ((annotationType != null) && (annotationType.getDeclaration() != null)) {
        annotations.put(annotationType.getDeclaration().getQualifiedName(), annotationMirror);
      }
    }
    return annotations;
  }

  //Inherited.
  public String getDocComment() {
    return delegate.getDocComment();
  }

  //Inherited.
  public Collection<AnnotationMirror> getAnnotationMirrors() {
    return DeclarationDecorator.decorateAnnotationMirrors(delegate.getAnnotationMirrors());
  }

  //Inherited.
  public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
    return delegate.getAnnotation(annotationType);
  }

  //Inherited.
  public Collection<Modifier> getModifiers() {
    return delegate.getModifiers();
  }

  //Inherited.
  public String getSimpleName() {
    return delegate.getSimpleName();
  }

  //Inherited.
  public SourcePosition getPosition() {
    return delegate.getPosition();
  }

  //Inherited.
  public void accept(DeclarationVisitor v) {
    v.visitDeclaration(this);
  }

  public Declaration getDelegate() {
    return delegate;
  }

  //Inherited.
  public boolean equals(Object obj) {
    return delegate.equals(obj);
  }

  //Inherited.
  public String toString() {
    return delegate.toString();
  }

}
