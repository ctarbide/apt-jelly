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
package net.sf.jelly.apt.decorations.type;

import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.type.ClassType;
import com.sun.mirror.util.TypeVisitor;
import net.sf.jelly.apt.decorations.DeclarationDecorator;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;

/**
 * @author Ryan Heaton
 */
public class DecoratedClassType extends DecoratedDeclaredType implements ClassType {

  public DecoratedClassType(ClassType delegate) {
    super(delegate);
  }

  public ClassType getSuperclass() {
    return TypeMirrorDecorator.decorate(((ClassType)delegate).getSuperclass());
  }

  public ClassDeclaration getDeclaration() {
    return DeclarationDecorator.decorate(((ClassType) delegate).getDeclaration());
  }

  public boolean isClass() {
    return true;
  }

  public boolean isInstanceOf(String className) {
    if (super.isInstanceOf(className)) {
      return true;
    }

    DecoratedClassType type = (DecoratedClassType) getSuperclass();
    return (type != null) && (type.isInstanceOf(className));
  }

  public void accept(TypeVisitor v) {
    v.visitClassType(this);
  }
}
