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

import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.AnnotationTypeElementDeclaration;
import com.sun.mirror.declaration.AnnotationValue;
import com.sun.mirror.util.DeclarationVisitor;
import net.sf.jelly.apt.decorations.DeclarationDecorator;

/**
 * @author Ryan Heaton
 */
public class DecoratedAnnotationTypeElementDeclaration extends DecoratedMethodDeclaration implements AnnotationTypeElementDeclaration {

  public DecoratedAnnotationTypeElementDeclaration(AnnotationTypeElementDeclaration delegate) {
    super(delegate);
  }

  public AnnotationValue getDefaultValue() {
    return ((AnnotationTypeElementDeclaration) delegate).getDefaultValue();
  }

  public AnnotationTypeDeclaration getDeclaringType() {
    return DeclarationDecorator.decorate(((AnnotationTypeElementDeclaration) delegate).getDeclaringType());
  }

  //Inherited.
  public void accept(DeclarationVisitor v) {
    v.visitAnnotationTypeElementDeclaration(this);
  }
}
