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

package net.sf.jelly.apt.tags;

import com.sun.mirror.type.TypeMirror;
import net.sf.jelly.apt.APTJellyTag;
import net.sf.jelly.apt.strategies.WrapIfPrimitiveStrategy;

/**
 * Wrap body if type is primitive.
 *
 * @author Ryan Heaton
 */
public class WrapIfPrimitiveTag extends APTJellyTag<WrapIfPrimitiveStrategy> {

  public WrapIfPrimitiveTag() {
    super(new WrapIfPrimitiveStrategy());
  }

  /**
   * The type to check for primitiveness.
   *
   * @param type The type to check for primitiveness.
   */
  public void setType(TypeMirror type) {
    this.strategy.setType(type);
  }
}
