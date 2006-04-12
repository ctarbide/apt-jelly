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

import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;

import java.io.IOException;

/**
 * A strategy for using template output.
 *
 * @author Ryan Heaton
 */
public interface TemplateStrategy<B extends TemplateBlock> {

  /**
   * Invoke the strategy with the specified data model and output.
   *
   * @param model The data model.
   * @param output The output.
   */
  <E extends Exception> void invoke(TemplateModel model, TemplateOutput<B, E> output) throws E, IOException, TemplateException;

}
