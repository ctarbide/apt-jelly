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

import com.sun.mirror.declaration.*;
import com.sun.mirror.type.InterfaceType;
import com.sun.mirror.util.DeclarationVisitor;
import net.sf.jelly.apt.decorations.DeclarationDecorator;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;

import java.util.Collection;

/**
 * A decorated type declaration provides:
 *
 * <ul>
 *   <li>boolean properties for the "type" of type declaration.
 * </ul>
 *
 * @author Ryan Heaton
 */
public class DecoratedTypeDeclaration extends DecoratedMemberDeclaration implements TypeDeclaration {

  public DecoratedTypeDeclaration(TypeDeclaration delegate) {
    super(delegate);
  }

  public PackageDeclaration getPackage() {
    return DeclarationDecorator.decorate(((TypeDeclaration)delegate).getPackage());
  }

  public String getQualifiedName() {
    return ((TypeDeclaration)delegate).getQualifiedName();
  }

  public Collection<TypeParameterDeclaration> getFormalTypeParameters() {
    return DeclarationDecorator.decorate(((TypeDeclaration)delegate).getFormalTypeParameters());
  }

  public Collection<InterfaceType> getSuperinterfaces() {
    return TypeMirrorDecorator.decorate(((TypeDeclaration)delegate).getSuperinterfaces());
  }

  public Collection<FieldDeclaration> getFields() {
    return DeclarationDecorator.decorate(((TypeDeclaration)delegate).getFields());
  }

  public Collection<TypeDeclaration> getNestedTypes() {
    return DeclarationDecorator.decorate(((TypeDeclaration)delegate).getNestedTypes());
  }

  public Collection<? extends MethodDeclaration> getMethods() {
    return DeclarationDecorator.decorate(((TypeDeclaration)delegate).getMethods());
  }

  public boolean isClass() {
    return false;
  }

  public boolean isInterface() {
    return false;
  }

  public boolean isEnum() {
    return false;
  }

  public boolean isAnnotatedType() {
    return false;
  }

  //Inherited.
  public void accept(DeclarationVisitor v) {
    v.visitTypeDeclaration(this);
  }
}
