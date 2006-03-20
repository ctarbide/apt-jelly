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

import com.sun.mirror.type.TypeMirror;
import com.sun.mirror.util.TypeVisitor;

import java.util.Collection;

/**
 * A decorated type mirror provides:
 *
 * <ul>
 *   <li>boolean properties to determine the type of mirror
 *   <li>an <code>isInstanceOf</code> method that determines whether this mirrors a class that is
 *       an instanceof the class denoted by the given fully-qualified name.
 *   <li>a docComment property that outputs the doc comment, if specified.  E.g. for a return type
 *       or a thrown type.
 * </ul>
 *
 * @author Ryan Heaton
 */
public class DecoratedTypeMirror implements TypeMirror {

  protected final TypeMirror delegate;
  private String docComment = "";

  public DecoratedTypeMirror(TypeMirror delegate) {
    this.delegate = delegate;
  }

  public void accept(TypeVisitor v) {
    v.visitTypeMirror(this);
  }

  public boolean equals(Object obj) {
    return delegate.equals(obj);
  }

  public String toString() {
    return delegate.toString();
  }

  public boolean isInstanceOf(String className) {
    return false;
  }

  public boolean isAnnotation() {
    return false;
  }

  public boolean isArray() {
    return false;
  }

  public boolean isCollection() {
    return isInstanceOf(Collection.class.getName());
  }

  public boolean isClass() {
    return false;
  }

  public boolean isDeclared() {
    return false;
  }

  public boolean isEnum() {
    return false;
  }

  public boolean isInterface() {
    return false;
  }

  public boolean isPrimitive() {
    return false;
  }

  public boolean isReferenceType() {
    return false;
  }

  public boolean isTypeVariable() {
    return false;
  }

  public boolean isVoid() {
    return false;
  }

  public boolean isWildcard() {
    return false;
  }

  public String getDocComment() {
    return docComment;
  }

  public void setDocComment(String docComment) {
    this.docComment = docComment;
  }

}
