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
package net.sf.jelly.apt.tags;

import com.sun.mirror.declaration.Declaration;
import net.sf.jelly.apt.strategies.AnnotationValueStrategy;

/**
 * Outputs an annotation value.  If a declaration is specified, it will be used to lookup the annotation value.
 * Otherwise, this tag must be nested within a declaration loop tag and the current declaration will be used.  If
 * a default value is specified, it will be output if either (1) the specified annotation doesn't exist, (2) the
 * specified annotation element doesn't exist, or (3) the value of the specified annotation element is its default
 * value.  If no default value is specified, the declared default value of the element will be output (if there is one).
 * Otherwise, nothing will be output.
 *
 * @author Ryan Heaton
 */
public class AnnotationValueTag extends APTJellyTag<AnnotationValueStrategy> {

  public AnnotationValueTag() {
    super(new AnnotationValueStrategy());
  }

  /**
   * The declaration on which to get the annotation value.
   *
   * @param declaration The declaration on which to get the annotation value.
   */
  public void setDeclaration(Declaration declaration) {
    strategy.setDeclaration(declaration);
  }

  /**
   * The default to return under any of the following circumstances:
   * <p/>
   * <ul>
   * <li>the annotation wasn't found</li>
   * <li>the specified type element wasn't found</li>
   * <li>the value of the specified type element was its default value.</li>
   * </ul>
   *
   * @param defaultValue The default value.
   */
  public void setDefault(String defaultValue) {
    strategy.setDefault(defaultValue);
  }

  /**
   * The specified annotation.
   *
   * @param annotation The specified annotation.
   */
  public void setAnnotation(String annotation) {
    strategy.setAnnotation(annotation);
  }

  /**
   * The type element.  Default is "value".
   *
   * @param element The type element.  Default is "value".
   */
  public void setElement(String element) {
    strategy.setElement(element);
  }
}
