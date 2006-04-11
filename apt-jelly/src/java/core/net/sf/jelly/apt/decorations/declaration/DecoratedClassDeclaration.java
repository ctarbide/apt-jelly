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

import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.ConstructorDeclaration;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.type.ClassType;
import com.sun.mirror.util.DeclarationVisitor;
import net.sf.jelly.apt.decorations.DeclarationDecorator;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;

import java.util.Collection;

/**
 * @author Ryan Heaton
 */
public class DecoratedClassDeclaration extends DecoratedTypeDeclaration implements ClassDeclaration {

  public DecoratedClassDeclaration(ClassDeclaration delegate) {
    super(delegate);
  }

  public ClassType getSuperclass() {
    return TypeMirrorDecorator.decorate((((ClassDeclaration)delegate).getSuperclass()));
  }

  public Collection<ConstructorDeclaration> getConstructors() {
    return DeclarationDecorator.decorate((((ClassDeclaration)delegate).getConstructors()));
  }

  public Collection<MethodDeclaration> getMethods() {
    return DeclarationDecorator.decorate(((ClassDeclaration)delegate).getMethods());
  }

  public boolean isClass() {
    return true;
  }

  //Inherited.
  public void accept(DeclarationVisitor v) {
    v.visitClassDeclaration(this);
  }

}
