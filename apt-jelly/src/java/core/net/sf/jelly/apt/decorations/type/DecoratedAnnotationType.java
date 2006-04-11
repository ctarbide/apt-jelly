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

import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.type.AnnotationType;
import com.sun.mirror.util.TypeVisitor;
import net.sf.jelly.apt.decorations.DeclarationDecorator;

/**
 * @author Ryan Heaton
 */
public class DecoratedAnnotationType extends DecoratedInterfaceType implements AnnotationType {

  public DecoratedAnnotationType(AnnotationType delegate) {
    super(delegate);
  }

  public AnnotationTypeDeclaration getDeclaration() {
    return DeclarationDecorator.decorate(((AnnotationType) delegate).getDeclaration());
  }

  public boolean isAnnotation() {
    return true;
  }

  public void accept(TypeVisitor v) {
    v.visitAnnotationType(this);
  }
}
