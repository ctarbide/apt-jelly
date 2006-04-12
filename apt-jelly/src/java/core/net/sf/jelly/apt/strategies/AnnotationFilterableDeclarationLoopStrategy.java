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

package net.sf.jelly.apt.strategies;

import com.sun.mirror.declaration.AnnotationMirror;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.Declaration;
import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.TemplateException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.io.IOException;

/**
 * A declaration loop that is filterable by an annotation class.
 *
 * @author Ryan Heaton
 */
public abstract class AnnotationFilterableDeclarationLoopStrategy<D extends Declaration, B extends TemplateBlock> extends DeclarationLoopStrategy<D, B> {

  private String annotation;
  private String annotationVar;

  public AnnotationFilterableDeclarationLoopStrategy(B block) {
    super(block);
  }

  @Override
  protected <E extends Exception>void invoke(D declaration, TemplateModel model, TemplateOutput<B, E> output) throws E, IOException, TemplateException {
    if (annotationVar != null) {
      Collection<AnnotationMirror> annotations = getCurrentDeclaration().getAnnotationMirrors();
      for (AnnotationMirror mirror : annotations) {
        AnnotationTypeDeclaration annotation = mirror.getAnnotationType().getDeclaration();
        if (annotation != null) {
          if (annotation.getQualifiedName().equals(this.annotation)) {
            model.setVariable(annotationVar, mirror);
          }
        }
      }
    }

    super.invoke(declaration, model, output);
  }

  /**
   * Get the list of all declarations to consider.
   *
   * @return The list of all declarations to consider.
   */
  public abstract Collection<D> getAllDeclarationsToConsiderForAnnotationFiltering() throws MissingParameterException;

  /**
   * The filtered list of declarations.
   *
   * @return The filtered list of declarations.
   */
  public Collection<D> getDeclarations() throws MissingParameterException {
    if (annotation == null) {
      return getAllDeclarationsToConsiderForAnnotationFiltering();
    }

    //I can't guarantee that the collection of unfiltered declarations is modifiable,
    //so I'm putting them in my own ArrayList.
    ArrayList<D> declarations = new ArrayList<D>(getAllDeclarationsToConsiderForAnnotationFiltering());
    Iterator<D> it = declarations.iterator();
    while (it.hasNext()) {
      D declaration = it.next();
      if (!hasAnnotation(declaration, annotation)) {
        it.remove();
      }
    }
    return declarations;
  }

  /**
   * Whether the given declaration is annotated with an annotation that has the given
   * (fully-qualified) annotation name.
   *
   * @param declaration The declaration.
   * @param annotationName The annotation name.
   * @return Whether the declaration has the annotation.
   */
  protected boolean hasAnnotation(D declaration, String annotationName) {
    for (AnnotationMirror mirror : declaration.getAnnotationMirrors()) {
      AnnotationTypeDeclaration annotation = mirror.getAnnotationType().getDeclaration();
      if (annotation != null) {
        return annotation.getQualifiedName().equals(annotationName);
      }
    }
    return false;
  }

  /**
   * Optional annotation by which to filter the classes.
   *
   * @return Optional annotation by which to filter the classes.
   */
  public String getAnnotation() {
    return annotation;
  }

  /**
   * Optional annotation by which to filter the classes.
   *
   * @param annotation Optional annotation by which to filter the classes.
   */
  public void setAnnotation(String annotation) {
    this.annotation = annotation;
  }

  /**
   * The context variable in which to store the annotation, if {@link #setAnnotation(String) specified}.
   *
   * @return The context variable in which to store the annotation, if {@link #setAnnotation(String) specified}.
   */
  public String getAnnotationVar() {
    return annotationVar;
  }

  /**
   * The context variable in which to store the annotation, if {@link #setAnnotation(String) specified}.
   *
   * @param annotationVar The context variable in which to store the annotation, if {@link #setAnnotation(String) specified}.
   */
  public void setAnnotationVar(String annotationVar) {
    this.annotationVar = annotationVar;
  }
}
