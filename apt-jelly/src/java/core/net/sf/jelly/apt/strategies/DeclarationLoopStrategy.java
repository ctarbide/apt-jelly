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
import java.util.Iterator;

import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.decorations.DeclarationDecorator;

/**
 * Invokes the template body over a set of declarations.
 *
 * @author Ryan Heaton
 */
public abstract class DeclarationLoopStrategy<D extends Declaration, B extends TemplateBlock> extends TemplateLoopStrategy<D, B> {

  private String var;
  private String indexVar;
  private D currentDeclaration;

  /**
   * The loop variable is the collection of declarations over which to loop.
   * @return The iterator over the collection of declarations.
   */
  protected Iterator<D> getLoop() throws TemplateException {
    return getDeclarations().iterator();
  }

  //Inherited.
  @Override
  protected void setupModelForLoop(TemplateModel model, D declaration, int index) throws TemplateException {
    super.setupModelForLoop(model, declaration, index);

    this.currentDeclaration = DeclarationDecorator.decorate(declaration);

    if (this.var != null) {
      model.setVariable(this.var, currentDeclaration);
    }

    if (indexVar != null) {
      model.setVariable(indexVar, index);
    }
  }

  /**
   * Sets up the model for the current loop.
   *
   * @param model The model.
   * @param index The loop index.
   */
  protected void setupModelForLoop(TemplateModel model, int index) {
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
