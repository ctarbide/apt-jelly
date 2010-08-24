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
import com.sun.mirror.declaration.InterfaceDeclaration;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.type.*;
import com.sun.mirror.util.DeclarationVisitor;
import com.sun.mirror.util.Declarations;
import net.sf.jelly.apt.Context;
import net.sf.jelly.apt.decorations.JavaDoc;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;
import net.sf.jelly.apt.decorations.type.DecoratedTypeMirror;
import net.sf.jelly.apt.util.JavaDocTagHandler;

import java.beans.Introspector;
import java.util.*;
import java.util.regex.Pattern;

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

  public static final Pattern INHERITDOC_PATTERN = Pattern.compile("\\{@inheritDoc.*?\\}");

  public DecoratedMethodDeclaration(MethodDeclaration delegate) {
    super(delegate);
  }

  @Override
  protected JavaDoc constructJavaDoc(String docComment, final JavaDocTagHandler handler) {
    if (docComment == null || "".equals(docComment.trim()) || INHERITDOC_PATTERN.matcher(docComment.trim()).matches()) {
      docComment = findInheritedDocComment();
    }

    return super.constructJavaDoc(docComment, handler);
  }

  private String findInheritedDocComment() {
    return findInheritedDocComment(new TreeSet<String>(), ((MethodDeclaration) getDelegate()).getDeclaringType());
  }

  private String findInheritedDocComment(Set<String> visitedDecls, TypeDeclaration declaringType) {
    if (declaringType != null) {
      Declarations declarations = Context.getCurrentEnvironment().getDeclarationUtils();
      Collection<InterfaceType> superIfaces = declaringType.getSuperinterfaces();
      if (superIfaces != null) {
        Collection<TypeDeclaration> decls = new ArrayList<TypeDeclaration>(superIfaces.size());
        for (InterfaceType superIface : superIfaces) {
          InterfaceDeclaration decl = superIface.getDeclaration();
          if (decl != null && !visitedDecls.contains(decl.getQualifiedName())) {
            visitedDecls.add(decl.getQualifiedName());
            for (MethodDeclaration methodDeclaration : decl.getMethods()) {

              MethodDeclaration decl1 = (MethodDeclaration) getDelegate();
              while (decl1 instanceof DecoratedMethodDeclaration) {
                decl1 = (MethodDeclaration) ((DecoratedMethodDeclaration) decl1).getDelegate();
              }

              MethodDeclaration decl2 = methodDeclaration;
              while (decl2 instanceof DecoratedMethodDeclaration) {
                decl2 = (MethodDeclaration) ((DecoratedMethodDeclaration) decl2).getDelegate();
              }

              if (declarations.overrides(decl1, decl2)) {
                String docComment = decl2.getDocComment();
                if (docComment == null || "".equals(docComment.trim()) || INHERITDOC_PATTERN.matcher(docComment.trim()).matches()) {
                  continue;
                }
                return docComment;
              }
            }
            decls.add(decl);
          }
        }

        for (TypeDeclaration decl : decls) {
          String docComment = findInheritedDocComment(visitedDecls, decl);
          if (docComment != null) {
            return docComment;
          }
        }
      }

      if (declaringType instanceof ClassDeclaration) {
        ClassType superClass = ((ClassDeclaration) declaringType).getSuperclass();
        if (superClass != null) {
          ClassDeclaration decl = superClass.getDeclaration();
          if (decl != null
            && !Object.class.getName().equals(decl.getQualifiedName())
            && !visitedDecls.contains(decl.getQualifiedName())) {

            visitedDecls.add(decl.getQualifiedName());
            for (MethodDeclaration methodDeclaration : decl.getMethods()) {

              MethodDeclaration decl1 = (MethodDeclaration) getDelegate();
              while (decl1 instanceof DecoratedMethodDeclaration) {
                decl1 = (MethodDeclaration) ((DecoratedMethodDeclaration) decl1).getDelegate();
              }

              MethodDeclaration decl2 = methodDeclaration;
              while (decl2 instanceof DecoratedMethodDeclaration) {
                decl2 = (MethodDeclaration) ((DecoratedMethodDeclaration) decl2).getDelegate();
              }

              if (declarations.overrides(decl1, decl2)) {
                String docComment = methodDeclaration.getDocComment();
                if (docComment == null || "".equals(docComment.trim()) || INHERITDOC_PATTERN.matcher(docComment.trim()).matches()) {
                  continue;
                }
                return docComment;
              }
            }

            return findInheritedDocComment(visitedDecls, decl);
          }
        }
      }
    }

    return null;
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
