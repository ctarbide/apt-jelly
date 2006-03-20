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

import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.type.PrimitiveType;
import com.sun.mirror.type.TypeMirror;
import com.sun.mirror.util.DeclarationVisitor;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;
import net.sf.jelly.apt.decorations.type.DecoratedTypeMirror;

import java.beans.Introspector;

/**
 * A decorated method declaration provides:
 *
 * <ul>
 *   <li>boolean properties for whether the method is a "getter" or "setter".
 *   <li>string property for the property name of this method if it is a "getter" or "setter".
 * </ul>
 *
 * @author Ryan Heaton
 */
public class DecoratedMethodDeclaration extends DecoratedExecutableDeclaration implements MethodDeclaration {

  public DecoratedMethodDeclaration(MethodDeclaration delegate) {
    super(delegate);
  }

  public TypeMirror getReturnType() {
    DecoratedTypeMirror typeMirror = (DecoratedTypeMirror) TypeMirrorDecorator.decorate(((MethodDeclaration) delegate).getReturnType());
    if (getJavaDoc().get("return") != null) {
      typeMirror.setDocComment(getJavaDoc().get("return").toString());
    }
    return typeMirror;
  }

  public boolean isGetter() {
    return (getSimpleName().startsWith("get") || isIs());
  }

  private boolean isIs() {
    return (getSimpleName().startsWith("is") && (getReturnType() instanceof PrimitiveType) &&
      (((PrimitiveType) getReturnType()).getKind() == PrimitiveType.Kind.BOOLEAN));
  }

  public boolean isSetter() {
    return (getSimpleName().startsWith("set"));
  }

  public String getPropertyName() {
    String propertyName = null;

    if (isIs()) {
      propertyName = Introspector.decapitalize(getSimpleName().substring(2));
    }
    else if (isGetter() || (isSetter())) {
      propertyName = Introspector.decapitalize(getSimpleName().substring(3));
    }

    return propertyName;
  }

  //Inherited.
  public void accept(DeclarationVisitor v) {
    v.visitMethodDeclaration(this);
  }

}
