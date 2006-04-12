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

import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.TemplateException;

import java.util.ArrayList;
import java.util.Collection;
import java.io.IOException;

/**
 * Evaluates its body for all methods of the {@link net.sf.jelly.apt.tags.ForAllTypesTag#getCurrentDeclaration() current type declaration}.
 *
 * @author Ryan Heaton
 */
public class MethodDeclarationLoopStrategy<B extends TemplateBlock> extends ExecutableDeclarationLoopStrategy<MethodDeclaration, B> {

  private String returnTypeVar;

  public MethodDeclarationLoopStrategy(B block) {
    super(block);
  }

  /**
   * The method declarations of the given type declaration.
   *
   * @return The method declarations of the given type declaration.
   */
  protected Collection<MethodDeclaration> getMemberDeclarations(TypeDeclaration declaration) {
    return new ArrayList<MethodDeclaration>(declaration.getMethods());
  }

  /**
   * Sets up the return type variable and the return type declaration variable, if desired.
   *
   * @param declaration The method declaration.
   * @param model The data model.
   * @param output The output.
   */
  @Override
  protected <E extends Exception>void invoke(MethodDeclaration declaration, TemplateModel model, TemplateOutput<B, E> output) throws E, IOException, TemplateException {
    if (returnTypeVar != null) {
      model.setVariable(returnTypeVar, declaration.getReturnType());
    }

    super.invoke(declaration, model, output);
  }

  /**
   * The variable to which to assign the return type (as a {@link com.sun.mirror.type.TypeMirror}).
   *
   * @return The variable to which to assign the return type (as a {@link com.sun.mirror.type.TypeMirror}).
   */
  public String getReturnTypeVar() {
    return returnTypeVar;
  }

  /**
   * The variable to which to assign the return type (as a {@link com.sun.mirror.type.TypeMirror}).
   *
   * @param returnTypeVar The variable to which to assign the return type (as a {@link com.sun.mirror.type.TypeMirror}).
   */
  public void setReturnTypeVar(String returnTypeVar) {
    this.returnTypeVar = returnTypeVar;
  }
}
