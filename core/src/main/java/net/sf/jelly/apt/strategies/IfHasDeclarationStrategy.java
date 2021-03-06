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

import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.type.DeclaredType;
import com.sun.mirror.type.TypeMirror;
import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;

import java.io.IOException;

/**
 * Evalute the body of the tag if the specified type has a declaration.  If the specified type is an
 * array type, the body will not be evaluated.
 *
 * @author Ryan Heaton
 */
public class IfHasDeclarationStrategy<B extends TemplateBlock> extends TemplateBlockStrategy<B> {

  private TypeMirror type;
  private String declarationVar;

  @Override
  public boolean preProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    super.preProcess(block, output, model);

    if (type == null) {
      throw new MissingParameterException("type");
    }

    if (type instanceof DeclaredType) {
      TypeDeclaration declaration = ((DeclaredType) type).getDeclaration();
      if (declaration != null) {
        if (declarationVar != null) {
          model.setVariable(declarationVar, declaration);
        }
      }

      return true;
    }

    return false;
  }

  /**
   * The type on which to check for a declaration.
   *
   * @return The type on which to check for a declaration.
   */
  public TypeMirror getType() {
    return type;
  }

  /**
   * The type on which to check for a declaration.
   *
   * @param type The type on which to check for a declaration.
   */
  public void setType(TypeMirror type) {
    this.type = type;
  }

  /**
   * The variable to which to assign the declaration variable, if it exists.
   *
   * @return The variable to which to assign the declaration variable, if it exists.
   */
  public String getDeclarationVar() {
    return declarationVar;
  }

  /**
   * The variable to which to assign the declaration variable, if it exists.
   *
   * @param declarationVar The variable to which to assign the declaration variable, if it exists.
   */
  public void setDeclarationVar(String declarationVar) {
    this.declarationVar = declarationVar;
  }
}
