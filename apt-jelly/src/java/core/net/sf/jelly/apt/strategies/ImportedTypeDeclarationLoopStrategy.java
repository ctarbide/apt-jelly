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

package net.sf.jelly.apt.strategies;

import com.sun.mirror.declaration.*;
import com.sun.mirror.type.*;
import com.sun.mirror.util.SimpleTypeVisitor;
import com.sun.mirror.util.TypeVisitor;

import java.util.Collection;
import java.util.HashMap;

import net.sf.jelly.apt.TemplateBlock;

/**
 * Evaluates its body for all imported types of a specified type declaration.  By default,
 * both classes and interfaces are included.  This list will include the bounds of any formal
 * type parameters.  This list will NOT include annotations used by the specified type
 * declaration.
 *
 * @author Ryan Heaton
 */
public class ImportedTypeDeclarationLoopStrategy<B extends TemplateBlock> extends TypeDeclarationLoopStrategy<B> {
  private TypeDeclaration declaration;

  public ImportedTypeDeclarationLoopStrategy(B block) {
    super(block);
    setIncludeClasses(true);
    setIncludeInterfaces(true);
  }

  protected Collection<TypeDeclaration> getAllTypeDeclarations() throws MissingParameterException {
    TypeDeclaration declaration = getDeclaration();
    if (declaration == null) {
      throw new MissingParameterException("declaration");
    }

    return getAllImportedTypes(declaration);
  }

  /**
   * Get all imported types of a given type declaration.
   *
   * @param typeDeclaration The type declaration from which to get all imported types.
   * @return The imported types of a given type declaration.
   */
  public static Collection<TypeDeclaration> getAllImportedTypes(TypeDeclaration typeDeclaration) {
    final HashMap<String, TypeDeclaration> importedTypes = new HashMap<String, TypeDeclaration>();

    //this visitor will add any type declarations to the list of imported types.
    TypeVisitor visitor = new SimpleTypeVisitor() {
      public void visitDeclaredType(DeclaredType declaredType) {
        TypeDeclaration declaration = declaredType.getDeclaration();
        if (declaration != null) {
          String qualifiedName = declaration.getQualifiedName();
          if (!qualifiedName.startsWith("java.lang")) {
            importedTypes.put(qualifiedName, declaration);
          }
        }

        for (TypeMirror type : declaredType.getActualTypeArguments()) {
          type.accept(this);
        }
      }

      public void visitArrayType(ArrayType arrayType) {
        arrayType.getComponentType().accept(this);
      }

      public void visitTypeVariable(TypeVariable typeVariable) {
        TypeParameterDeclaration declaration = typeVariable.getDeclaration();
        if (declaration != null) {
          Collection<ReferenceType> bounds = declaration.getBounds();
          for (ReferenceType referenceType : bounds) {
            referenceType.accept(this);
          }
        }
      }
    };

    //visit the fields.
    for (FieldDeclaration field : typeDeclaration.getFields()) {
      field.getType().accept(visitor);
    }

    //visit the methods.
    for (MethodDeclaration method : typeDeclaration.getMethods()) {
      method.getReturnType().accept(visitor);

      for (ParameterDeclaration parameter : method.getParameters()) {
        parameter.getType().accept(visitor);
      }

      for (ReferenceType thrownType : method.getThrownTypes()) {
        thrownType.accept(visitor);
      }

      for (TypeParameterDeclaration formalTypeParameter : method.getFormalTypeParameters()) {
        Collection<ReferenceType> bounds = formalTypeParameter.getBounds();
        for (ReferenceType bound : bounds) {
          bound.accept(visitor);
        }
      }
    }

    //visit the superinterfaces.
    for (InterfaceType interfaceType : typeDeclaration.getSuperinterfaces()) {
      interfaceType.accept(visitor);
    }

    //visit the superclasses and constructors.
    if (typeDeclaration instanceof ClassDeclaration) {
      ((ClassDeclaration) typeDeclaration).getSuperclass().accept(visitor);

      for (ConstructorDeclaration constructor : ((ClassDeclaration) typeDeclaration).getConstructors()) {
        for (ParameterDeclaration parameter : constructor.getParameters()) {
          parameter.getType().accept(visitor);
        }

        for (ReferenceType thrownType : constructor.getThrownTypes()) {
          thrownType.accept(visitor);
        }

        for (TypeParameterDeclaration formalTypeParameter : constructor.getFormalTypeParameters()) {
          Collection<ReferenceType> bounds = formalTypeParameter.getBounds();
          for (ReferenceType bound : bounds) {
            bound.accept(visitor);
          }
        }
      }
    }

    for (TypeParameterDeclaration formalTypeParameter : typeDeclaration.getFormalTypeParameters()) {
      Collection<ReferenceType> bounds = formalTypeParameter.getBounds();
      for (ReferenceType bound : bounds) {
        bound.accept(visitor);
      }
    }

    return importedTypes.values();
  }

  /**
   * The type declaration for which to get all imported types.
   *
   * @return The type declaration for which to get all imported types.
   */
  public TypeDeclaration getDeclaration() {
    return declaration;
  }

  /**
   * The type declaration for which to get all imported types.
   *
   * @param declaration The type declaration for which to get all imported types.
   */
  public void setDeclaration(TypeDeclaration declaration) {
    this.declaration = declaration;
  }
}
