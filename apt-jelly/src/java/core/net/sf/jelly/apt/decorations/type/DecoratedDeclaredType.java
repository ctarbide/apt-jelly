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

import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.type.DeclaredType;
import com.sun.mirror.type.InterfaceType;
import com.sun.mirror.type.TypeMirror;
import com.sun.mirror.util.TypeVisitor;
import net.sf.jelly.apt.decorations.DeclarationDecorator;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;
import net.sf.jelly.apt.decorations.declaration.DecoratedTypeDeclaration;

import java.util.Collection;

/**
 * A decorated declared type provides a property for getting its actual type parameters as a list
 * (so they can be accessed with the [] operator in expression language).
 *
 * @author Ryan Heaton
 */
public class DecoratedDeclaredType extends DecoratedReferenceType implements DeclaredType {

  public DecoratedDeclaredType(DeclaredType delegate) {
    super(delegate);
  }

  public TypeDeclaration getDeclaration() {
    return DeclarationDecorator.decorate(((DeclaredType) delegate).getDeclaration());
  }

  public DeclaredType getContainingType() {
    return TypeMirrorDecorator.decorate(((DeclaredType)delegate).getContainingType());
  }

  public Collection<TypeMirror> getActualTypeArguments() {
    return TypeMirrorDecorator.decorate(((DeclaredType) delegate).getActualTypeArguments());
  }

  public Collection<InterfaceType> getSuperinterfaces() {
    return TypeMirrorDecorator.decorate(((DeclaredType)delegate).getSuperinterfaces());
  }

  public boolean isDeclared() {
    return true;
  }

  public boolean isInstanceOf(String className) {
    DecoratedTypeDeclaration declaration = (DecoratedTypeDeclaration) getDeclaration();
    if ((declaration != null) && (declaration.getQualifiedName().equals(className))) {
      return true;
    }

    Collection<InterfaceType> superinterfaces = declaration.getSuperinterfaces();
    for (InterfaceType interfaceType : superinterfaces) {
      DecoratedInterfaceType decorated = (DecoratedInterfaceType) interfaceType;
      if (decorated.isInstanceOf(className)) {
        return true;
      }
    }

    return false;
  }

  public void accept(TypeVisitor v) {
    v.visitDeclaredType(this);
  }
}
