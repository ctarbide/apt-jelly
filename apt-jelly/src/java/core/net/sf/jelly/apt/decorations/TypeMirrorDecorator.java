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
package net.sf.jelly.apt.decorations;

import com.sun.mirror.type.*;
import com.sun.mirror.util.TypeVisitor;
import net.sf.jelly.apt.decorations.type.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Decorates a {@link TypeMirror} when visited.
 *
 * @author Ryan Heaton
 */
public class TypeMirrorDecorator implements TypeVisitor {

  private TypeMirror decoratedTypeMirror;

  /**
   * Gets an instance of <code>TypeMirrorDecorator</code>.  If there is a system property named
   * "net.sf.jelly.apt.decorations.TypeMirrorDecorator" that has a value that is the fully-qualified classname
   * of a class that extends <code>TypeMirrorDecorator</code>, an attempt will be made to return
   * an instance of that.  If for any reason this attempt fails, the system property will be removed and an instance
   * of the default decorator will be returned.
   *
   * @return an instance of <code>TypeMirrorDecorator</code>.
   */
  protected static TypeMirrorDecorator getInstance() {
    String decoratorClassname = System.getProperty(TypeMirrorDecorator.class.getName());
    if (decoratorClassname != null) {
      try {
        return (TypeMirrorDecorator) Class.forName(decoratorClassname).newInstance();
      }
      catch (Exception e) {
        System.getProperties().remove(TypeMirrorDecorator.class.getName());
        e.printStackTrace(System.err);
        System.err.println("Unable to lookup TypeMirrorDecorator.  Using default decorator...");
      }
    }

    return new TypeMirrorDecorator();
  }

  /**
   * Decorate a type mirror.
   *
   * @param typeMirror The mirror to decorate.
   * @return The decorated type mirror.
   */
  @SuppressWarnings({"unchecked"})
  public static <T extends TypeMirror> T decorate(T typeMirror) {
    if (typeMirror == null) {
      return null;
    }

    TypeMirrorDecorator decorator = getInstance();
    typeMirror.accept(decorator);
    return (T) decorator.getDecoratedTypeMirror();
  }

  /**
   * Decorate a collection fo type mirrors.
   *
   * @param typeMirrors The type mirrors to decorate.
   * @return The collection of decorated type mirrors.
   */
  @SuppressWarnings({"unchecked"})
  public static <T extends TypeMirror> Collection<T> decorate(Collection<T> typeMirrors) {
    if (typeMirrors == null) {
      return null;
    }

    ArrayList<T> mirrors = new ArrayList<T>(typeMirrors.size());
    TypeMirrorDecorator decorator = getInstance();
    for (T mirror : typeMirrors) {
      mirror.accept(decorator);
      mirrors.add((T) decorator.getDecoratedTypeMirror());
    }
    return mirrors;
  }

  /**
   * Get the decorated type mirror.
   *
   * @return The decorated type mirror, or <code>null</code> if none has been set.
   */
  public TypeMirror getDecoratedTypeMirror() {
    return decoratedTypeMirror;
  }

  /**
   * Decorates <code>typeMirror</code> and sets it as the {@link #getDecoratedTypeMirror() decorated type mirror}.
   *
   * @param typeMirror The declaration to decorate.
   */
  public void visitTypeMirror(TypeMirror typeMirror) {
    if (typeMirror instanceof DecoratedTypeMirror) {
      this.decoratedTypeMirror = typeMirror;
    }
    else {
      this.decoratedTypeMirror = new DecoratedTypeMirror(typeMirror);
    }
  }

  /**
   * Decorates <code>typeMirror</code> and sets it as the {@link #getDecoratedTypeMirror() decorated type mirror}.
   *
   * @param typeMirror The declaration to decorate.
   */
  public void visitPrimitiveType(PrimitiveType typeMirror) {
    if (typeMirror instanceof DecoratedPrimitiveType) {
      this.decoratedTypeMirror = typeMirror;
    }
    else {
      this.decoratedTypeMirror = new DecoratedPrimitiveType(typeMirror);
    }
  }

  /**
   * Decorates <code>typeMirror</code> and sets it as the {@link #getDecoratedTypeMirror() decorated type mirror}.
   *
   * @param typeMirror The declaration to decorate.
   */
  public void visitVoidType(VoidType typeMirror) {
    if (typeMirror instanceof DecoratedVoidType) {
      this.decoratedTypeMirror = typeMirror;
    }
    else {
      this.decoratedTypeMirror = new DecoratedVoidType(typeMirror);
    }
  }

  /**
   * Decorates <code>typeMirror</code> and sets it as the {@link #getDecoratedTypeMirror() decorated type mirror}.
   *
   * @param typeMirror The declaration to decorate.
   */
  public void visitReferenceType(ReferenceType typeMirror) {
    if (typeMirror instanceof DecoratedReferenceType) {
      this.decoratedTypeMirror = typeMirror;
    }
    else {
      this.decoratedTypeMirror = new DecoratedReferenceType(typeMirror);
    }
  }

  /**
   * Decorates <code>typeMirror</code> and sets it as the {@link #getDecoratedTypeMirror() decorated type mirror}.
   *
   * @param typeMirror The declaration to decorate.
   */
  public void visitDeclaredType(DeclaredType typeMirror) {
    if (typeMirror instanceof DecoratedDeclaredType) {
      this.decoratedTypeMirror = typeMirror;
    }
    else {
      this.decoratedTypeMirror = new DecoratedDeclaredType(typeMirror);
    }
  }

  /**
   * Decorates <code>typeMirror</code> and sets it as the {@link #getDecoratedTypeMirror() decorated type mirror}.
   *
   * @param typeMirror The declaration to decorate.
   */
  public void visitClassType(ClassType typeMirror) {
    if (typeMirror instanceof DecoratedClassType) {
      this.decoratedTypeMirror = typeMirror;
    }
    else {
      this.decoratedTypeMirror = new DecoratedClassType(typeMirror);
    }
  }

  /**
   * Decorates <code>typeMirror</code> and sets it as the {@link #getDecoratedTypeMirror() decorated type mirror}.
   *
   * @param typeMirror The declaration to decorate.
   */
  public void visitEnumType(EnumType typeMirror) {
    if (typeMirror instanceof DecoratedEnumType) {
      this.decoratedTypeMirror = typeMirror;
    }
    else {
      this.decoratedTypeMirror = new DecoratedEnumType(typeMirror);
    }
  }

  /**
   * Decorates <code>typeMirror</code> and sets it as the {@link #getDecoratedTypeMirror() decorated type mirror}.
   *
   * @param typeMirror The declaration to decorate.
   */
  public void visitInterfaceType(InterfaceType typeMirror) {
    if (typeMirror instanceof DecoratedInterfaceType) {
      this.decoratedTypeMirror = typeMirror;
    }
    else {
      this.decoratedTypeMirror = new DecoratedInterfaceType(typeMirror);
    }
  }

  /**
   * Decorates <code>typeMirror</code> and sets it as the {@link #getDecoratedTypeMirror() decorated type mirror}.
   *
   * @param typeMirror The declaration to decorate.
   */
  public void visitAnnotationType(AnnotationType typeMirror) {
    if (typeMirror instanceof DecoratedAnnotationType) {
      this.decoratedTypeMirror = typeMirror;
    }
    else {
      this.decoratedTypeMirror = new DecoratedAnnotationType(typeMirror);
    }
  }

  /**
   * Decorates <code>typeMirror</code> and sets it as the {@link #getDecoratedTypeMirror() decorated type mirror}.
   *
   * @param typeMirror The declaration to decorate.
   */
  public void visitArrayType(ArrayType typeMirror) {
    if (typeMirror instanceof DecoratedArrayType) {
      this.decoratedTypeMirror = typeMirror;
    }
    else {
      this.decoratedTypeMirror = new DecoratedArrayType(typeMirror);
    }
  }

  /**
   * Decorates <code>typeMirror</code> and sets it as the {@link #getDecoratedTypeMirror() decorated type mirror}.
   *
   * @param typeMirror The declaration to decorate.
   */
  public void visitTypeVariable(TypeVariable typeMirror) {
    if (typeMirror instanceof DecoratedTypeVariable) {
      this.decoratedTypeMirror = typeMirror;
    }
    else {
      this.decoratedTypeMirror = new DecoratedTypeVariable(typeMirror);
    }
  }

  /**
   * Decorates <code>typeMirror</code> and sets it as the {@link #getDecoratedTypeMirror() decorated type mirror}.
   *
   * @param typeMirror The declaration to decorate.
   */
  public void visitWildcardType(WildcardType typeMirror) {
    if (typeMirror instanceof DecoratedWildcardType) {
      this.decoratedTypeMirror = typeMirror;
    }
    else {
      this.decoratedTypeMirror = new DecoratedWildcardType(typeMirror);
    }
  }

}
