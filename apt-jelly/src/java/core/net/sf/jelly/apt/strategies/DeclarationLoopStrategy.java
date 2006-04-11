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

import com.sun.mirror.declaration.Declaration;

import java.util.Collection;

import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateBody;
import net.sf.jelly.apt.decorations.DeclarationDecorator;

/**
 * Invokes the template body over a set of declarations.
 *
 * @author Ryan Heaton
 */
public abstract class DeclarationLoopStrategy<D extends Declaration> implements TemplateBodyLoopStrategy {

  private String var;
  private String indexVar;
  private D currentDeclaration;
  private int index = 0;

  //Inherited.
  public <E extends Exception> void invoke(TemplateModel model, TemplateBody<E> body) throws E, MissingParameterException {
    Collection<D> declarations = getDeclarations();
    index = 0;
    for (D declaration : declarations) {
      //decorate the declaration first.
      declaration = DeclarationDecorator.decorate(declaration);
      invoke(declaration, model, body);
    }
  }

  /**
   * Invoke the body with the specified declaration as the current declaration.
   *
   * @param declaration The declaration specified as the current declaration.
   * @param model The model.
   * @param body The body.
   */
  protected <E extends Exception> void invoke(D declaration, TemplateModel model, TemplateBody<E> body) throws E, MissingParameterException {
    currentDeclaration = declaration;

    if (var != null) {
      model.setVariable(var, declaration);
    }

    if (indexVar != null) {
      model.setVariable(indexVar, index);
    }

    body.invoke();

    index++;
  }

  /**
   * The current declaration in the loop.
   *
   * @return The current declaration in the loop.
   */
  public D getCurrentDeclaration() {
    return currentDeclaration;
  }

  /**
   * The declarations to loop through.
   *
   * @return The declarations to loop through.
   */
  public abstract Collection<D> getDeclarations() throws MissingParameterException;

  /**
   * The context variable in which to store the declaration.
   *
   * @return The context variable in which to store the declaration.
   */
  public String getVar() {
    return var;
  }

  /**
   * The context variable in which to store the declaration.
   *
   * @param var The context variable in which to store the declaration.
   */
  public void setVar(String var) {
    this.var = var;
  }

  /**
   * Variable in which to store the value of the index.
   *
   * @return Variable in which to store the value of the index.
   */
  public String getIndexVar() {
    return indexVar;
  }

  /**
   * Variable in which to store the value of the index.
   *
   * @param indexVar Variable in which to store the value of the index.
   */
  public void setIndexVar(String indexVar) {
    this.indexVar = indexVar;
  }


}
