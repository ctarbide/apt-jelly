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
 * Writes a value to template output.
 *
 * @author Ryan Heaton
 */
public abstract class TemplateValueStrategy<B extends TemplateBlock> extends TemplateStrategyControl<B> {

  /**
   * Get the value to write to the template output.
   *
   * @return The value to write to the template output.
   */
  protected abstract String getValue() throws TemplateException;

  /**
   * Writes the {@link #getValue() value} to the output.
   *
   * @param block  The block.
   * @param output The output.
   * @param model  The model.
   * @return false, as no body should be processed.
   */
  public boolean preProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    String value = getValue();
    if (value != null) {
      output.write(value);
    }

    return false;
  }

  /**
   * @throws UnsupportedOperationException Because a value strategy shouldn't have a body.
   */
  public boolean processBody(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    throw new UnsupportedOperationException("A value strategy shouldn't have a body.");
  }

  /**
   * Do nothing.
   */
  public void postProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    //no-op.
  }

}
