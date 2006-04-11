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
package net.sf.jelly.apt.decorations;

import com.sun.mirror.declaration.*;
import com.sun.mirror.util.DeclarationVisitor;
import net.sf.jelly.apt.decorations.declaration.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Decorates a {@link Declaration} when visited.
 *
 * @author Ryan Heaton
 */
public class DeclarationDecorator implements DeclarationVisitor {

  private Declaration decoratedDeclaration;

  /**
   * Gets an instance of <code>TypeMirrorDecorator</code>.  If there is a system property named
   * "net.sf.jelly.apt.decorations.DeclarationDecorator" that has a value that is the fully-qualified classname
   * of a class that extends <code>DeclarationDecorator</code>, an attempt will be made to return
   * an instance of that.  If for any reason this attempt fails, the system property will be removed and an instance
   * of the default decorator will be returned.
   *
   * @return an instance of <code>DeclarationDecorator</code>.
   */
  protected static DeclarationDecorator getInstance() {
    String decoratorClassname = System.getProperty(DeclarationDecorator.class.getName());
    if (decoratorClassname != null) {
      try {
        return (DeclarationDecorator) Class.forName(decoratorClassname).newInstance();
      }
      catch (Exception e) {
        System.getProperties().remove(DeclarationDecorator.class.getName());
        e.printStackTrace(System.err);
        System.err.println("Unable to lookup DeclarationDecorator.  Using default decorator...");
      }
    }

    return new DeclarationDecorator();
  }

  /**
   * Decorates a declaration.
   *
   * @param declaration The declaration to decorate.
   * @return The decorated declaration.
   */
  @SuppressWarnings({"unchecked"})
  public static <D extends Declaration> D decorate(D declaration) {
    if (declaration == null) {
      return null;
    }

    DeclarationDecorator decorator = getInstance();
    declaration.accept(decorator);
    return (D) decorator.getDecoratedDeclaration();
  }

  /**
   * Decorates a collection of declarations.
   *
   * @param declarations The declarations to decorate.
   * @return The decorated declarations.
   */
  @SuppressWarnings({"unchecked"})
  public static <D extends Declaration> Collection<D> decorate(Collection<D> declarations) {
    if (declarations == null) {
      return null;
    }

    DeclarationDecorator decorator = getInstance();
    ArrayList<D> decls = new ArrayList<D>(declarations.size());
    for (D declaration : declarations) {
      declaration.accept(decorator);
      decls.add((D) decorator.getDecoratedDeclaration());
    }
    return decls;
  }

  /**
   * Decorates a collection of annotation mirrors.
   *
   * @param annotationMirrors The annotation mirrors to decorate.
   * @return The collection of decorated annotation mirrors.
   */
  public static Collection<AnnotationMirror> decorateAnnotationMirrors(Collection<AnnotationMirror> annotationMirrors) {
    if (annotationMirrors == null) {
      return null;
    }

    DeclarationDecorator decorator = getInstance();
    ArrayList<AnnotationMirror> mirrors = new ArrayList<AnnotationMirror>(annotationMirrors.size());
    for (AnnotationMirror annotationMirror : annotationMirrors) {
      if (!(annotationMirror instanceof DecoratedAnnotationMirror)) {
        //AnnotationMirror is neither a TypeMirror nor a Declaration...
        annotationMirror = decorator.decorate(annotationMirror);
      }

      mirrors.add(annotationMirror);
    }

    return mirrors;
  }

  /**
   * Gets the decorated declaration.
   *
   * @return The decorated declaration, or <code>null</code> if none has been set.
   */
  public Declaration getDecoratedDeclaration() {
    return decoratedDeclaration;
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitDeclaration(Declaration declaration) {
    if (declaration instanceof DecoratedDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitPackageDeclaration(PackageDeclaration declaration) {
    if (declaration instanceof DecoratedPackageDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedPackageDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitMemberDeclaration(MemberDeclaration declaration) {
    if (declaration instanceof DecoratedMemberDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedMemberDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitTypeDeclaration(TypeDeclaration declaration) {
    if (declaration instanceof DecoratedTypeDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedTypeDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitClassDeclaration(ClassDeclaration declaration) {
    if (declaration instanceof DecoratedClassDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedClassDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitEnumDeclaration(EnumDeclaration declaration) {
    if (declaration instanceof DecoratedEnumDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedEnumDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitInterfaceDeclaration(InterfaceDeclaration declaration) {
    if (declaration instanceof DecoratedInterfaceDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedInterfaceDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitAnnotationTypeDeclaration(AnnotationTypeDeclaration declaration) {
    if (declaration instanceof DecoratedAnnotationTypeDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedAnnotationTypeDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitFieldDeclaration(FieldDeclaration declaration) {
    if (declaration instanceof DecoratedFieldDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedFieldDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitEnumConstantDeclaration(EnumConstantDeclaration declaration) {
    if (declaration instanceof DecoratedEnumConstantDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedEnumConstantDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitExecutableDeclaration(ExecutableDeclaration declaration) {
    if (declaration instanceof DecoratedExecutableDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedExecutableDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitConstructorDeclaration(ConstructorDeclaration declaration) {
    if (declaration instanceof DecoratedConstructorDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedConstructorDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitMethodDeclaration(MethodDeclaration declaration) {
    if (declaration instanceof DecoratedMethodDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedMethodDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitAnnotationTypeElementDeclaration(AnnotationTypeElementDeclaration declaration) {
    if (declaration instanceof DecoratedAnnotationTypeElementDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedAnnotationTypeElementDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitParameterDeclaration(ParameterDeclaration declaration) {
    if (declaration instanceof DecoratedParameterDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedParameterDeclaration(declaration);
    }
  }

  /**
   * Decorates <code>declaration</code> and sets it as the {@link #getDecoratedDeclaration() decorated declaration}.
   *
   * @param declaration The declaration to decorate.
   */
  public void visitTypeParameterDeclaration(TypeParameterDeclaration declaration) {
    if (declaration instanceof DecoratedTypeParameterDeclaration) {
      this.decoratedDeclaration = declaration;
    }
    else {
      this.decoratedDeclaration = new DecoratedTypeParameterDeclaration(declaration);
    }
  }

  /**
   * Decorate an annotation mirror.
   *
   * @param annotationMirror The annotation mirror to decorate.
   * @return The decorated annotation mirror.
   */
  public DecoratedAnnotationMirror decorate(AnnotationMirror annotationMirror) {
    return new DecoratedAnnotationMirror(annotationMirror);
  }

}
