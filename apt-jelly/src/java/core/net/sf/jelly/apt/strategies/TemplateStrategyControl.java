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
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;

import java.io.IOException;

/**
 * Base strategy class that goes through a specific control process when invoked.
 *
 * @author Ryan Heaton
 */
public abstract class TemplateStrategyControl<B extends TemplateBlock> implements TemplateStrategy<B> {

  /**
   * Pre-process the strategy.
   *
   * @return Whether or not evaluation of the body is necessary.
   */
  public abstract boolean preProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException;

  /**
   * Process the body of the block.
   *
   * @param block The block.
   * @param output The output.
   * @param model The model.
   *
   * @return Whether the body should be evaluated again.
   */
  public abstract boolean processBody(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException;

  /**
   * Post-process the strategy.
   *
   * @param block The block.
   * @param output The output.
   * @param model The model.
   */
  public abstract void postProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException;

  /**
   * Invoke the strategy using the control process.
   *
   * @param block The block.
   * @param output The output.
   * @param model The model.
   */
  public void invoke(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    if (preProcess(block, output, model)) {
      while (processBody(block, output, model));
    }
    postProcess(block, output, model);
  }


}
