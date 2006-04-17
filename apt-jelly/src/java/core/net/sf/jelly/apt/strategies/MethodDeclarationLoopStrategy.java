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
import net.sf.jelly.apt.TemplateException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Evaluates its body for all methods of the a type declaration.
 *
 * @author Ryan Heaton
 */
public class MethodDeclarationLoopStrategy<B extends TemplateBlock> extends ExecutableDeclarationLoopStrategy<MethodDeclaration, B> {

  private String returnTypeVar;

  /**
   * The method declarations of the given type declaration.
   *
   * @return The method declarations of the given type declaration.
   */
  protected Collection<MethodDeclaration> getMemberDeclarations(TypeDeclaration declaration) {
    return new ArrayList<MethodDeclaration>(declaration.getMethods());
  }

  //Inherited.
  @Override
  protected void setupModelForLoop(TemplateModel model, MethodDeclaration declaration, int index) throws TemplateException {
    super.setupModelForLoop(model, declaration, index);

    if (returnTypeVar != null) {
      model.setVariable(returnTypeVar, getCurrentDeclaration().getReturnType());
    }

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
