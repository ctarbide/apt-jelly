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

import com.sun.mirror.declaration.ExecutableDeclaration;
import com.sun.mirror.type.ReferenceType;
import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.MissingAttributeException;
import org.apache.commons.jelly.XMLOutput;

import java.util.Collection;

/**
 * Iterates through each thrown type of the {@link ExecutableDeclarationLoopTag#getCurrentDeclaration() current executable
 * declaration}.
 *
 * @author Ryan Heaton
 */
public class ForAllThrownTypesTag extends APTTagSupport {

  private String var;
  private String indexVar;
  private ExecutableDeclaration declaration;

  public void doTag(XMLOutput output) throws MissingAttributeException, JellyTagException {
    Collection<ReferenceType> thrownTypes = getThrownTypes();

    int index = 0;
    for (ReferenceType thrownType : thrownTypes) {
      if (indexVar != null) {
        getContext().setVariable(indexVar, index);
      }

      invokeBody(thrownType, output);

      index++;
    }
  }

  /**
   * Invoke the body with the given thrown type as the current thrown type.
   *
   * @param thrownType The thrown type.
   * @param output The output
   */
  protected void invokeBody(ReferenceType thrownType, XMLOutput output) throws JellyTagException {
    if (var != null) {
      getContext().setVariable(var, thrownType);
    }

    invokeBody(output);
  }

  /**
   * Get the thrown types.
   *
   * @return The thrown types.
   */
  protected Collection<ReferenceType> getThrownTypes() throws JellyTagException {
    ExecutableDeclaration declaration = getDeclaration();
    if (declaration == null) {
      declaration = getCurrentExecutableDeclaration();

      if (declaration == null) {
        throw new JellyTagException("The loop tag for thrown types must either be within a loop tag for exectuable declarations or the declaration must be specified.");
      }
    }

    return declaration.getThrownTypes();
  }

  /**
   * Get the {@link net.sf.jelly.apt.tags.ExecutableDeclarationLoopTag#getCurrentDeclaration() current executable declaration}
   *
   * @return The current executable declaration.
   * @throws JellyTagException If this tag isn't in an executable declaration loop.
   */
  protected ExecutableDeclaration getCurrentExecutableDeclaration() throws JellyTagException {
    ExecutableDeclarationLoopTag tag = (ExecutableDeclarationLoopTag) findAncestorWithClass(ExecutableDeclarationLoopTag.class);
    return ((tag == null) ? null : tag.getCurrentDeclaration());
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
