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

package net.sf.jelly.apt.freemarker;

import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.strategies.TemplateStrategyControl;
import net.sf.jelly.apt.strategies.MissingParameterException;

import java.io.Writer;
import java.io.IOException;
import java.io.FilterWriter;

import freemarker.template.TransformControl;
import freemarker.template.TemplateModelException;

/**
 * A freemarker template block.  The block buffers its value and invokes the strategy when finished.
 *
 * @author Ryan Heaton
 */
public class FreemarkerTemplateBlock extends FilterWriter implements TemplateBlock, TemplateOutput<FreemarkerTemplateBlock>, TransformControl {

  private final TemplateStrategyControl strategy;
  private final String transformName;

  //Inherited.
  protected FreemarkerTemplateBlock(Writer out, TemplateStrategyControl strategy, String transformName) {
    super(out);
    this.strategy = strategy;
    this.transformName = transformName;
  }

  /**
   * Pre-process the strategy.
   *
   * @return Whether to evaluate the block.
   */
  public int onStart() throws TemplateModelException, IOException {
    boolean go;

    try {
      go = strategy.preProcess(this, this, FreemarkerModel.get());
    }
    catch (MissingParameterException mpe) {
      throw new TemplateModelException("The '" + transformName + "' transform requires the '" + mpe.getParameter() + "' attribute");
    }
    catch (TemplateException e) {
      throw new TemplateModelException(e);
    }

    return go ? TransformControl.EVALUATE_BODY : TransformControl.SKIP_BODY;
  }

  /**
   * Process the strategy.
   *
   * @return Whether to continue the loop.
   */
  public int afterBody() throws TemplateModelException, IOException {
    boolean loop;

    try {
      loop = strategy.processBody(this, this, FreemarkerModel.get());
    }
    catch (MissingParameterException mpe) {
      throw new TemplateModelException("The '" + transformName + "' transform requires the '" + mpe.getParameter() + "' attribute");
    }
    catch (TemplateException e) {
      throw new TemplateModelException(e);
    }

    return loop ? TransformControl.REPEAT_EVALUATION : TransformControl.END_EVALUATION;
  }

  /**
   * Close and post-process the strategy.
   *
   * @throws IOException
   */
  @Override
  public void close() throws IOException {
    try {
      strategy.postProcess(this, this, FreemarkerModel.get());
    }
    catch (TemplateException e) {
      throw new RuntimeException(e);
    }

    super.close();
  }

  /**
   * Just throw it.
   */
  public void onError(Throwable throwable) throws Throwable {
    throw throwable;
  }

  /**
   * Redirect the output.
   */
  public void redirect(FreemarkerTemplateBlock block, Writer writer) throws IOException, TemplateException {
    this.out = writer;
    this.lock = writer;
  }

  /**
   * No-op, as the work of writing the block will be done by Freemarker.
   */
  public void write(FreemarkerTemplateBlock block) throws IOException, TemplateException {
    //no-op.
  }

}
