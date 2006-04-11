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

import com.sun.mirror.type.PrimitiveType;
import com.sun.mirror.util.TypeVisitor;

/**
 * A decorated type mirror provides:
 *
 * <ul>
 *   <li>A string property denoting the java keyword for its {@link #getKind() kind}.
 * </ul>
 *
 * @author Ryan Heaton
 */
public class DecoratedPrimitiveType extends DecoratedTypeMirror implements PrimitiveType {

  public DecoratedPrimitiveType(PrimitiveType delegate) {
    super(delegate);
  }

  public Kind getKind() {
    return ((PrimitiveType)delegate).getKind();
  }

  public boolean isInstanceOf(String className) {
    return getKeyword().equals(className);
  }

  public boolean isPrimitive() {
    return true;
  }

  public String getKeyword() {
    return String.valueOf(getKind()).toLowerCase();
  }

  public void accept(TypeVisitor v) {
    v.visitPrimitiveType(this);
  }
}
