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

import com.sun.mirror.type.PrimitiveType;
import com.sun.mirror.type.TypeMirror;
import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;

import java.io.IOException;

/**
 * Writes out code that unboxes its body in from wrapper object if the specified type is primitive.
 *
 * @author Ryan Heaton
 */
public class UnwrapIfPrimitiveStrategy<B extends TemplateBlock> extends TemplateBlockStrategy<B> {

  private TypeMirror type;

  @Override
  public void postProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    super.postProcess(block, output, model);

    if (type instanceof PrimitiveType) {
      String post;

      switch (((PrimitiveType) type).getKind()) {
        case BOOLEAN:
          post = ".booleanValue()";
          break;
        case BYTE:
          post = ".byteValue()";
          break;
        case CHAR:
          post = ".charValue()";
          break;
        case DOUBLE:
          post = ".doubleValue()";
          break;
        case FLOAT:
          post = ".floatValue()";
          break;
        case INT:
          post = ".intValue()";
          break;
        case LONG:
          post = ".longValue()";
          break;
        case SHORT:
          post = ".shortValue()";
          break;
        default:
          post = "";
          break;
      }

      output.write(post);
    }
  }

  /**
   * The type to check for primitiveness.
   *
   * @return The type to check for primitiveness.
   */
  public TypeMirror getType() {
    return type;
  }

  /**
   * The type to check for primitiveness.
   *
   * @param type The type to check for primitiveness.
   */
  public void setType(TypeMirror type) {
    this.type = type;
  }


}
