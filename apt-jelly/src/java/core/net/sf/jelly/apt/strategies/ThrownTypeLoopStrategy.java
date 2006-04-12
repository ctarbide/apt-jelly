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

import com.sun.mirror.declaration.ExecutableDeclaration;
import com.sun.mirror.type.ReferenceType;

import java.util.Collection;
import java.io.IOException;

import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.TemplateException;

/**
 * Iterates through each thrown type of the specified executable declaration.
 *
 * @author Ryan Heaton
 */
public class ThrownTypeLoopStrategy<B extends TemplateBlock> extends TemplateBlockStrategy<B> {

  private String var;
  private String indexVar;
  private ExecutableDeclaration declaration;

  public ThrownTypeLoopStrategy(B block) {
    super(block);
  }

  @Override
  public <E extends Exception> void invoke(TemplateModel model, TemplateOutput<B, E> output) throws E, IOException, TemplateException {
    Collection<ReferenceType> thrownTypes = getThrownTypes();

    int index = 0;
    for (ReferenceType thrownType : thrownTypes) {
      if (indexVar != null) {
        model.setVariable(indexVar, index);
      }

      if (var != null) {
        model.setVariable(var, thrownType);
      }

      super.invoke(model, output);

      index++;
    }
  }

  /**
   * Get the thrown types.
   *
   * @return The thrown types.
   */
  protected Collection<ReferenceType> getThrownTypes() throws MissingParameterException {
    ExecutableDeclaration declaration = getDeclaration();
    if (declaration == null) {
      throw new MissingParameterException("declaration");
    }

    return declaration.getThrownTypes();
  }

  /**
   * The variable into which to store the current thrown type.
   *
   * @return The variable into which to store the current thrown type.
   */
  public String getVar() {
    return var;
  }

  /**
   * The variable into which to store the current thrown type.
   *
   * @param var The variable into which to store the current thrown type.
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

  /**
   * The specified declaration.
   *
   * @return The specified declaration.
   */
  public ExecutableDeclaration getDeclaration() {
    return declaration;
  }

  /**
   * The specified declaration.
   *
   * @param declaration The specified declaration.
   */
  public void setDeclaration(ExecutableDeclaration declaration) {
    this.declaration = declaration;
  }


}
