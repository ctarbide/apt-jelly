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

import java.util.Collection;
import java.io.IOException;

/**
 * Evalute the body of the tag if the specified declaration has a given annotation.
 *
 * @author Ryan Heaton
 */
public class IfHasAnnotationStrategy<B extends TemplateBlock> extends TemplateBlockStrategy<B> {

  private Declaration declaration;
  private String annotation;
  private String var;

  @Override
  public boolean preProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    super.preProcess(block, output, model);
    
    if (annotation == null) {
      throw new MissingParameterException("annotation");
    }

    Declaration declaration = getDeclaration();
    if (declaration == null) {
      declaration = getCurrentDeclaration();

      if (declaration == null) {
        throw new MissingParameterException("declaration");
      }
    }

    Collection<AnnotationMirror> annotations = declaration.getAnnotationMirrors();
    for (AnnotationMirror mirror : annotations) {
      AnnotationTypeDeclaration annotationDeclaration = mirror.getAnnotationType().getDeclaration();
      if ((annotationDeclaration != null) && (annotationDeclaration.getQualifiedName().equals(annotation))) {
        if (var != null) {
          model.setVariable(var, mirror);
        }

        return true;
      }
    }

    return false;
  }

  /**
   * The annotation to check for.
   *
   * @return The annotation to check for.
   */
  public String getAnnotation() {
    return annotation;
  }

  /**
   * The annotation to check for.
   *
   * @param annotation The annotation to check for.
   */
  public void setAnnotation(String annotation) {
    this.annotation = annotation;
  }

  /**
   * The name of the variable to which to assign the annotation.
   *
   * @return The name of the variable to which to assign the annotation.
   */
  public String getVar() {
    return var;
  }

  /**
   * The name of the variable to which to assign the annotation.
   *
   * @param var The name of the variable to which to assign the annotation.
   */
  public void setVar(String var) {
    this.var = var;
  }

  /**
   * The declaration on which to evaluate whether exists the annotation.
   *
   * @return The declaration on which to evaluate whether exists the annotation.
   */
  public Declaration getDeclaration() {
    return declaration;
  }

  /**
   * The declaration on which to evaluate whether exists the annotation.
   *
   * @param declaration The declaration on which to evaluate whether exists the annotation.
   */
  public void setDeclaration(Declaration declaration) {
    this.declaration = declaration;
  }

  /**
   * Gets the current declaration (in a loop).
   *
   * @return the current declaration (in a loop).
   */
  protected Declaration getCurrentDeclaration() {
    DeclarationLoopStrategy loop = StrategyStack.get().findFirst(DeclarationLoopStrategy.class);
    if (loop != null) {
      return loop.getCurrentDeclaration();
    }
    return null;
  }
}
