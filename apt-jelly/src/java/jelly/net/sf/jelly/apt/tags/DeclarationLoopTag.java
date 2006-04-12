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
import net.sf.jelly.apt.decorations.DeclarationDecorator;
import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.MissingAttributeException;
import org.apache.commons.jelly.XMLOutput;

import java.util.Collection;

/**
 * A tag that is the loop over a set of declarations.
 *
 * @author Ryan Heaton
 */
public abstract class DeclarationLoopTag<D extends Declaration> extends JellyTagSupport {

  private String var;
  private String indexVar;
  private D currentDeclaration;
  private int index = 0;

  public void doTag(XMLOutput output) throws MissingAttributeException, JellyTagException {
    Collection<D> declarations = getDeclarations();
    index = 0;
    for (D declaration : declarations) {

      //decorate the declaration first.
      declaration = DeclarationDecorator.decorate(declaration);
      invokeBody(declaration, output);
    }
  }

  /**
   * Invoke the body given <code>declaration</code> as the current declaration.
   *
   * @param declaration The current declaration.
   * @param output The output.
   */
  protected void invokeBody(D declaration, XMLOutput output) throws JellyTagException {
    currentDeclaration = declaration;

    if (var != null) {
      getContext().setVariable(var, declaration);
    }

    if (indexVar != null) {
      getContext().setVariable(indexVar, index);
    }

    invokeBody(output);

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
  public abstract Collection<D> getDeclarations() throws JellyTagException;

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
