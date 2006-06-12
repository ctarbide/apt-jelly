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

import com.sun.mirror.declaration.*;
import com.sun.mirror.type.InterfaceType;
import com.sun.mirror.util.DeclarationVisitor;
import net.sf.jelly.apt.decorations.DeclarationDecorator;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * A decorated type declaration provides:
 * <p/>
 * <ul>
 * <li>boolean properties for the "type" of type declaration.
 * </ul>
 *
 * @author Ryan Heaton
 */
public class DecoratedTypeDeclaration extends DecoratedMemberDeclaration implements TypeDeclaration {

  public DecoratedTypeDeclaration(TypeDeclaration delegate) {
    super(delegate);
  }

  public PackageDeclaration getPackage() {
    return DeclarationDecorator.decorate(((TypeDeclaration) delegate).getPackage());
  }

  public String getQualifiedName() {
    return ((TypeDeclaration) delegate).getQualifiedName();
  }

  public Collection<TypeParameterDeclaration> getFormalTypeParameters() {
    return DeclarationDecorator.decorate(((TypeDeclaration) delegate).getFormalTypeParameters());
  }

  public Collection<InterfaceType> getSuperinterfaces() {
    return TypeMirrorDecorator.decorate(((TypeDeclaration) delegate).getSuperinterfaces());
  }

  public Collection<FieldDeclaration> getFields() {
    return DeclarationDecorator.decorate(((TypeDeclaration) delegate).getFields());
  }

  public Collection<TypeDeclaration> getNestedTypes() {
    return DeclarationDecorator.decorate(((TypeDeclaration) delegate).getNestedTypes());
  }

  public Collection<? extends MethodDeclaration> getMethods() {
    return DeclarationDecorator.decorate(((TypeDeclaration) delegate).getMethods());
  }

  /**
   * The readable properties of this type declaration.
   *
   * @return The readable properties of this type declaration.
   */
  public Collection<PropertyDeclaration> getProperties() {
    HashMap<String, DecoratedMethodDeclaration> getters = new HashMap<String, DecoratedMethodDeclaration>();
    HashMap<String, DecoratedMethodDeclaration> setters = new HashMap<String, DecoratedMethodDeclaration>();
    for (MethodDeclaration method : getMethods()) {
      DecoratedMethodDeclaration decoratedMethod = (DecoratedMethodDeclaration) method;
      if (decoratedMethod.isGetter() || decoratedMethod.isSetter()) {
        HashMap<String, DecoratedMethodDeclaration> methodMap = decoratedMethod.isGetter() ? getters : setters;
        methodMap.put(decoratedMethod.getPropertyName(), decoratedMethod);
      }
    }

    ArrayList<PropertyDeclaration> properties = new ArrayList<PropertyDeclaration>(getters.size());
    //now iterate through the getters and setters and pair them up....
    for (String propertyName : getters.keySet()) {
      DecoratedMethodDeclaration getter = getters.get(propertyName);
      DecoratedMethodDeclaration setter = setters.get(propertyName);
      if (isPaired(getter, setter)) {
        properties.add(new PropertyDeclaration(getter, setter));
      }
    }

    return properties;
  }

  /**
   * Whether a specified getter and setter are paired.
   *
   * @param getter The getter.
   * @param setter The setter.
   * @return Whether a specified getter and setter are paired.
   */
  protected boolean isPaired(DecoratedMethodDeclaration getter, DecoratedMethodDeclaration setter) {
    if (getter == null) {
      return false;
    }

    if (!getter.isGetter()) {
      return false;
    }

    if (getter.getParameters().size() != 0) {
      return false;
    }

    if (setter != null) {
      if (!setter.isSetter()) {
        return false;
      }

      if (!getter.getPropertyName().equals(setter.getPropertyName())) {
        return false;
      }

      Collection<ParameterDeclaration> setterParams = setter.getParameters();
      if ((setterParams == null) || (setterParams.size() != 1) || (!getter.getReturnType().equals(setterParams.iterator().next().getType()))) {
        return false;
      }
    }

    return true;
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
