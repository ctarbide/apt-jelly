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
package net.sf.jelly.apt;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationMirror;
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.type.TypeMirror;
import net.sf.jelly.apt.decorations.DeclarationDecorator;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;
import net.sf.jelly.apt.decorations.declaration.DecoratedAnnotationMirror;
import org.apache.commons.jelly.JellyContext;

/**
 * A jelly context with an {@link AnnotationProcessorEnvironment}.
 *
 * @author Ryan Heaton
 */
public class APTJellyContext extends JellyContext {

  private final AnnotationProcessorEnvironment aptEnv;

  public APTJellyContext(AnnotationProcessorEnvironment env) {
    this.aptEnv = env;
  }

  protected APTJellyContext(AnnotationProcessorEnvironment env, JellyContext parent) {
    super(parent);
    this.aptEnv = env;
  }

  public AnnotationProcessorEnvironment getAnnotationProcessorEnvironment() {
    return aptEnv;
  }

  protected JellyContext createChildContext() {
    return new APTJellyContext(this.aptEnv, this);
  }

  /**
   * If <code>value</code> is a {@link Declaration} or {@link TypeMirror}, decorate
   * it before setting it in the context.
   *
   * @param name The name of the variable.
   * @param value The value of the variable.
   */
  public void setVariable(String name, Object value) {
    if (value instanceof Declaration) {
      value = DeclarationDecorator.decorate((Declaration) value);
    }
    else if (value instanceof TypeMirror) {
      value = TypeMirrorDecorator.decorate((TypeMirror) value);
    }
    else if ((value instanceof AnnotationMirror) && !(value instanceof DecoratedAnnotationMirror)) {
      //AnnotationMirror is neither a TypeMirror nor a Declaration...
      value = new DecoratedAnnotationMirror((AnnotationMirror) value);
    }

    super.setVariable(name, value);
  }

}
