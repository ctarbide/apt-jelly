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
import java.io.PrintWriter;

/**
 * A strategy for redirecting output.
 *
 * @author Ryan Heaton
 */
public abstract class TemplateOutputRedirectionStrategy<B extends TemplateBlock> extends TemplateBlockStrategy<B> {

  private PrintWriter writer;

  /**
   * @return The writer to which to redirect the output.
   */
  protected abstract PrintWriter getWriter() throws TemplateException, IOException;

  @Override
  public boolean preProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    super.preProcess(block, output, model);
    this.writer = getWriter();
    output.redirect(block, writer);
    return true;
  }

  @Override
  public void postProcess(B block, TemplateOutput<B> output, TemplateModel model) throws IOException, TemplateException {
    super.postProcess(block, output, model);
    if (this.writer != null) { //the writer could be null if there was an error in the pre-process.
      this.writer.close();
    }
  }

}
