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

import com.sun.mirror.declaration.FieldDeclaration;
import com.sun.mirror.type.TypeMirror;
import com.sun.mirror.util.DeclarationVisitor;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;

/**
 * @author Ryan Heaton
 */
public class DecoratedFieldDeclaration extends DecoratedMemberDeclaration implements FieldDeclaration {

  public DecoratedFieldDeclaration(FieldDeclaration delegate) {
    super(delegate);
  }

  public TypeMirror getType() {
    return TypeMirrorDecorator.decorate(((FieldDeclaration)delegate).getType());
  }

  public Object getConstantValue() {
    return ((FieldDeclaration)delegate).getConstantValue();
  }

  public String getConstantExpression() {
    return ((FieldDeclaration)delegate).getConstantExpression();
  }

  //Inherited.
  public void accept(DeclarationVisitor v) {
    v.visitFieldDeclaration(this);
  }
}
