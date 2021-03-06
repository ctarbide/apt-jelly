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

import com.sun.mirror.declaration.InterfaceDeclaration;
import com.sun.mirror.type.InterfaceType;
import com.sun.mirror.util.TypeVisitor;
import net.sf.jelly.apt.decorations.DeclarationDecorator;

/**
 * @author Ryan Heaton
 */
public class DecoratedInterfaceType extends DecoratedDeclaredType implements InterfaceType {

  public DecoratedInterfaceType(InterfaceType delegate) {
    super(delegate);
  }

  public InterfaceDeclaration getDeclaration() {
    return DeclarationDecorator.decorate(((InterfaceType) delegate).getDeclaration());
  }

  public boolean isInterface() {
    return true;
  }

  public void accept(TypeVisitor v) {
    v.visitInterfaceType(this);
  }
}
