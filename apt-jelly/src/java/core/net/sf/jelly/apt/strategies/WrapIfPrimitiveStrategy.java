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
 * Writes out code that boxes its body in a wrapper object constructor if the specified type is primitive.
 *
 * @author Ryan Heaton
 */
public class WrapIfPrimitiveStrategy<B extends TemplateBlock> extends TemplateBlockStrategy<B> {

  private TypeMirror type;

  @Override
  public boolean preProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    super.preProcess(block, output, model);

    if (type == null) {
      throw new MissingParameterException("type");
    }

    if (type instanceof PrimitiveType) {
      String pre;

      switch (((PrimitiveType) type).getKind()) {
        case BOOLEAN:
          pre = "new Boolean(";
          break;
        case BYTE:
          pre = "new Byte(";
          break;
        case CHAR:
          pre = "new Character(";
          break;
        case DOUBLE:
          pre = "new Double(";
          break;
        case FLOAT:
          pre = "new Float(";
          break;
        case INT:
          pre = "new Integer(";
          break;
        case LONG:
          pre = "new Long(";
          break;
        case SHORT:
          pre = "new Short(";
          break;
        default:
          pre = "";
          break;
      }

      output.write(pre);
    }

    return true;
  }

  @Override
  public void postProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    super.postProcess(block, output, model);

    if (type instanceof PrimitiveType) {
      output.write(")");
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
