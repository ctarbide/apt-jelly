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
import com.sun.mirror.type.*;
import com.sun.mirror.util.SimpleTypeVisitor;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.Map;

/**
 * Handles invocations of methods on annotation instances.
 *
 * @author Ryan Heaton
 * @see DecoratedDeclaration#getAnnotation
 */
public class AnnotationInvocationHandler implements InvocationHandler {

  private final Class annotationType;
  private final DecoratedAnnotationMirror annotationMirror;

  public <A extends Annotation> AnnotationInvocationHandler(Class<A> annotationType, DecoratedAnnotationMirror annotationMirror) {
    this.annotationType = annotationType;
    this.annotationMirror = annotationMirror;
  }

  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    String methodName = method.getName();
    int paramLength = method.getParameterTypes().length;
    if (("equals".equals(methodName)) && (paramLength == 1)) {
      //todo: implement according to the spec.
      return false;
    }
    else if (("hashCode".equals(methodName)) && (paramLength == 0)) {
      //todo: implement according to the spec.
      return hashCode();
    }
    else if (("annotationType".equals(methodName)) && (paramLength == 0)) {
      return annotationType;
    }
    else if (("toString".equals(methodName)) && (paramLength == 0)) {
      return "Proxy invocation handler for " + annotationType.getName();
    }
    else {
      Map<String, Object> allElementValues = annotationMirror.getAllElementValues();
      if (allElementValues.containsKey(methodName)) {
        Object value = allElementValues.get(methodName);
        Class realValueType = method.getReturnType();
        return getRealValue(value, realValueType);
      }
    }

    return null;
  }

  /**
   * Attempts to get the real value from the annotation element value.
   *
   * @param value         The value.
   * @param realValueType The real value type.
   * @return The real value.
   */
  protected Object getRealValue(Object value, Class realValueType) {
    Object realValue;
    if (value instanceof TypeMirror) {
      //The annotation element value is a class object.
      try {
        realValue = loadClass((TypeMirror) value);
      }
      catch (ClassNotFoundException e) {
        throw new MirroredTypeException((TypeMirror) value);
      }
    }
    else if (value instanceof EnumConstantDeclaration) {
      //it's an enum constant....
      EnumConstantDeclaration constant = (EnumConstantDeclaration) value;
      EnumDeclaration declaringType = constant.getDeclaringType();
      Class enumClass;
      try {
        enumClass = loadClass(getLoadableFQN(declaringType));
      }
      catch (ClassNotFoundException e) {
        throw new IllegalStateException("Unable to load the enum class " + declaringType.getQualifiedName() + " to create the instance of "
          + annotationType.getName() + " declared at " + annotationMirror.getPosition());
      }

      realValue = Enum.valueOf(enumClass, constant.getSimpleName());
    }
    else if (value instanceof AnnotationMirror) {
      //it's another annotation.
      DecoratedAnnotationMirror childMirror = new DecoratedAnnotationMirror((AnnotationMirror) value);
      realValue = Proxy.newProxyInstance(annotationType.getClassLoader(), new Class[]{realValueType}, new AnnotationInvocationHandler(realValueType, childMirror));
    }
    else if (value instanceof Collection) {
      //it's an array of more values...
      Collection<AnnotationValue> values = (Collection<AnnotationValue>) value;
      if (!realValueType.isArray()) {
        throw new IllegalArgumentException("Expected the real value type to be an array.  Actual: " + realValueType.getName());
      }

      Class componentType = realValueType.getComponentType();
      Object[] valueArray = (Object[]) Array.newInstance(componentType, values.size());
      int index = 0;
      for (AnnotationValue annotationValue : values) {
        valueArray[index] = getRealValue(annotationValue.getValue(), componentType);
        index++;
      }
      realValue = valueArray;
    }
    else {
      realValue = value;
    }

    return realValue;
  }

  protected Class loadClass(TypeMirror typeMirror) throws ClassNotFoundException {
    final boolean[] isPrimitive = new boolean[]{false};
    final String[] fqn = new String[]{null};
    typeMirror.accept(new SimpleTypeVisitor() {
      @Override
      public void visitPrimitiveType(PrimitiveType primitiveType) {
        isPrimitive[0] = true;
        switch (primitiveType.getKind()) {
          case BOOLEAN:
            fqn[0] = Boolean.class.getName();
            break;
          case BYTE:
            fqn[0] = Byte.class.getName();
            break;
          case CHAR:
            fqn[0] = Character.class.getName();
            break;
          case DOUBLE:
            fqn[0] = Double.class.getName();
            break;
          case FLOAT:
            fqn[0] = Float.class.getName();
            break;
          case INT:
            fqn[0] = Integer.class.getName();
            break;
          case LONG:
            fqn[0] = Long.class.getName();
            break;
          case SHORT:
            fqn[0] = Short.class.getName();
            break;
          default:
            throw new IllegalArgumentException("Unknown kind: " + primitiveType);
        }
      }

      @Override
      public void visitVoidType(VoidType voidType) {
        isPrimitive[0] = true;
        fqn[0] = Void.class.getName();
      }

      @Override
      public void visitDeclaredType(DeclaredType declaredType) {
        TypeDeclaration declaration = declaredType.getDeclaration();
        if (declaration != null) {
          fqn[0] = getLoadableFQN(declaration);
        }
      }

      @Override
      public void visitArrayType(ArrayType arrayType) {
        arrayType.getComponentType().accept(this);
        fqn[0] += "[]";
      }
    });

    if (fqn[0] != null) {
      Class clazz = loadClass(fqn[0]);
      if (isPrimitive[0]) {
        try {
          Field field = clazz.getField("TYPE");
          clazz = (Class) field.get(null);
        }
        catch (Exception e) {
          throw new ClassNotFoundException("Unable to load the primitive type " + typeMirror, e);
        }
      }
      return clazz;
    }

    throw new ClassNotFoundException("Class not found: " + typeMirror);
  }

  /**
   * Get the "loadable" fqn of the given declaration, where "loadable" means recognizable by
   * Class.forName.
   *
   * @param declaration The declaration.
   * @return The loadable fqn.
   */
  protected String getLoadableFQN(TypeDeclaration declaration) {
    StringBuilder className = new StringBuilder();
    TypeDeclaration declaringType = declaration.getDeclaringType();
    while (declaringType != null) {
      className.insert(0, "$" + declaration.getSimpleName());
      declaration = declaringType;
      declaringType = declaration.getDeclaringType();
    }

    className.insert(0, declaration.getQualifiedName());
    return className.toString();
  }

  protected Class loadClass(String qualifiedName) throws ClassNotFoundException {
    return Class.forName(qualifiedName, true, annotationType.getClassLoader());
  }

}
