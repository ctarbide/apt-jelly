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
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A declaration loop that is filterable by an annotation class.
 *
 * @author Ryan Heaton
 */
public abstract class AnnotationFilterableDeclarationLoopStrategy<D extends Declaration, B extends TemplateBlock> extends DeclarationLoopStrategy<D, B> {

  private String annotation;
  private String annotationVar;
  private String excludes;

  //Inherited.
  @Override
  protected void setupModelForLoop(TemplateModel model, D declaration, int index) throws TemplateException {
    super.setupModelForLoop(model, declaration, index);

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
    if ((annotation == null) && (excludes == null)) {
      return getAllDeclarationsToConsiderForAnnotationFiltering();
    }

    //I can't guarantee that the collection of unfiltered declarations is modifiable,
    //so I'm putting them in my own ArrayList.
    ArrayList<D> declarations = new ArrayList<D>(getAllDeclarationsToConsiderForAnnotationFiltering());
    if (annotation != null) {
      Iterator<D> it = declarations.iterator();
      while (it.hasNext()) {
        D declaration = it.next();
        if (!hasAnnotation(declaration, annotation)) {
          it.remove();
        }
      }
    }

    if (excludes != null) {
      HashSet<String> excludesSet = new HashSet<String>();
      for (String excludeAnnotation : excludes.split(",")) {
        excludeAnnotation = excludeAnnotation.trim();
        if (excludeAnnotation.length() > 0) {
          excludesSet.add(excludeAnnotation);
        }
      }

      Iterator<D> it = declarations.iterator();
      while (it.hasNext()) {
        D declaration = it.next();
        for (String exclude : excludesSet) {
          if (hasAnnotation(declaration, exclude)) {
            it.remove();
            break;
          }
        }
      }
    }


    return declarations;
  }

  /**
   * Whether the given declaration is annotated with an annotation that has the given
   * (fully-qualified) annotation name.
   *
   * @param declaration    The declaration.
   * @param annotationName The annotation name.
   * @return Whether the declaration has the annotation.
   */
  protected boolean hasAnnotation(D declaration, String annotationName) {
    for (AnnotationMirror mirror : declaration.getAnnotationMirrors()) {
      AnnotationTypeDeclaration annotation = mirror.getAnnotationType().getDeclaration();
      if ((annotation != null) && (annotation.getQualifiedName().equals(annotationName))) {
        return true;
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

  /**
   * Comma-separated list of annotations that will exclude the declaration from the loop.
   *
   * @return Comma-separated list of annotations that will exclude the declaration from the loop.
   */
  public String getExcludes() {
    return excludes;
  }

  /**
   * Comma-separated list of annotations that will exclude the declaration from the loop.
   *
   * @param excludes Comma-separated list of annotations that will exclude the declaration from the loop.
   */
  public void setExcludes(String excludes) {
    this.excludes = excludes;
  }
}
