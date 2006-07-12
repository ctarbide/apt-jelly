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

import net.sf.jelly.apt.strategies.AnnotationFilterableDeclarationLoopStrategy;

/**
 * A declaration loop tag that is filterable by an annotation class.
 *
 * @author Ryan Heaton
 */
public abstract class AnnotationFilterableDeclarationLoopTag<S extends AnnotationFilterableDeclarationLoopStrategy> extends DeclarationLoopTag<S> {

  protected AnnotationFilterableDeclarationLoopTag(S strategy) {
    super(strategy);
  }

  /**
   * Optional annotation by which to filter the classes.
   *
   * @param annotation Optional annotation by which to filter the classes.
   */
  public void setAnnotation(String annotation) {
    strategy.setAnnotation(annotation);
  }

  /**
   * The context variable in which to store the annotation, if {@link #setAnnotation(String) specified}.
   *
   * @param annotationVar The context variable in which to store the annotation, if {@link #setAnnotation(String) specified}.
   */
  public void setAnnotationVar(String annotationVar) {
    strategy.setAnnotationVar(annotationVar);
  }

  /**
   * Comma-separated list of annotations that will exclude the declaration from the loop.
   *
   * @param excludes Comma-separated list of annotations that will exclude the declaration from the loop.
   */
  public void setExcludes(String excludes) {
    strategy.setExcludes(excludes);
  }
}
