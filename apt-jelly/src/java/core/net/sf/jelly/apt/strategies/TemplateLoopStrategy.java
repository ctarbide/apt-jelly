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
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateException;

import java.io.IOException;
import java.util.Iterator;

/**
 * A template stategy that loops its block.
 *
 * @author Ryan Heaton
 */
public abstract class TemplateLoopStrategy<I, B extends TemplateBlock> extends TemplateBlockStrategy<B> {

  private Iterator<I> loop = null;
  private int index = 0;

  /**
   * Get the loop.
   *
   * @param model The model to use as necessary.
   * @return The loop.
   * @param model
   */
  protected abstract Iterator<I> getLoop(TemplateModel model) throws TemplateException;

  /**
   * Sets up the model for the current loop.  Default implementation does nothing.
   *
   * @param model The model.
   * @param var The loop variable.
   * @param index The loop index.
   */
  protected void setupModelForLoop(TemplateModel model, I var, int index) throws TemplateException {
    //default no-op.
  }

  /**
   * Gets the loop, and sets up the model for the first iteration.
   *
   * @param block The block.
   * @param output The output.
   * @param model The model.
   * @return Whether to evaluate the body.
   */
  @Override
  public boolean preProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    super.preProcess(block, output, model);

    this.loop = getLoop(model);
    this.index = 0;
    boolean hasNext = this.loop != null && this.loop.hasNext();
    if (hasNext) {
      setupModelForLoop(model, this.loop.next(), this.index++);
    }
    return hasNext;
  }

  /**
   * Set up the model for the loop and process the body.
   *
   * @param block The block.
   * @param output The output.
   * @param model The model.
   * @return Whether to do the loop again.
   */
  @Override
  public boolean processBody(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    if (this.loop == null) {
      throw new TemplateException("The body of a template loop cannot be processed without the loop!");
    }

    super.processBody(block, output, model);

    boolean hasNext = this.loop.hasNext();
    if (hasNext) {
      setupModelForLoop(model, this.loop.next(), this.index++);
    }
    return hasNext;
  }

}
