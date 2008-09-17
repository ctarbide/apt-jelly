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

import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.TemplateException;

import java.io.IOException;

/**
 * A strategy for outputing a template block.
 *
 * @author Ryan Heaton
 */
public abstract class TemplateBlockStrategy<B extends TemplateBlock> extends TemplateStrategyControl<B> {

  /**
   * Pushes this strategy on the stack and redirects the output.
   *
   * @param block The block.
   * @param output The output.
   * @param model The model.
   * @return true, as the body should be evaluated.
   */
  public boolean preProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    StrategyStack.get().push(this);
    return true;
  }

  /**
   * Writes the body to the output.
   *
   * @param block The block.
   * @param output The output.
   * @param model The model.
   * @return false.
   */
  public boolean processBody(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    output.write(block);
    return false;
  }

  /**
   * Pops this strategy off the stack.
   */
  public void postProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    StrategyStack.get().pop();
  }

}
