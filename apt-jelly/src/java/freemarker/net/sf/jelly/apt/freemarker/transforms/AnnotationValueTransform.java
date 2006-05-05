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

package net.sf.jelly.apt.freemarker.transforms;

import net.sf.jelly.apt.freemarker.FreemarkerTransform;
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
public class AnnotationValueTransform extends FreemarkerTransform<AnnotationValueStrategy> {

  /**
   * Construct a new transform under the specified namespace.  <code>null</code> or <code>""</code> means the root namespace.
   *
   * @param namespace The namespace.
   */
  public AnnotationValueTransform(String namespace) {
    super(namespace);
  }

  //Inherited.
  public AnnotationValueStrategy newStrategy() {
    return new AnnotationValueStrategy();
  }

}
