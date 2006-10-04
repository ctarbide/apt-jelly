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

/**
 * Outputs the name of the wrapper for the primitive specified by the "type" parameter.
 *
 * @author Ryan Heaton
 */
public class PrimitiveWrapperStrategy<B extends TemplateBlock> extends TemplateValueStrategy<B> {

  private TypeMirror type;

  protected String getValue() throws TemplateException {
    if (type == null) {
      throw new MissingParameterException("type");
    }


    if (!(type instanceof PrimitiveType)) {
      throw new TemplateException("'type' must be a primitive type.");
    }

    switch (((PrimitiveType) type).getKind()) {
      case BOOLEAN:
        return "Boolean";
      case BYTE:
        return "Byte";
      case CHAR:
        return "Character";
      case DOUBLE:
        return "Double";
      case FLOAT:
        return "Float";
      case INT:
        return "Integer";
      case LONG:
        return "Long";
      case SHORT:
        return "Short";
      default:
        throw new TemplateException("No wrapper for primitive type " + type);
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
