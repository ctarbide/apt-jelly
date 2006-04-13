/**
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

import net.sf.jelly.apt.APTJellyContext;
import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.strategies.TemplateStrategy;
import net.sf.jelly.apt.strategies.MissingParameterException;
import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.TagSupport;
import org.apache.commons.jelly.XMLOutput;
import org.apache.commons.jelly.MissingAttributeException;

import java.io.IOException;

/**
 * Tag support for jelly tags.
 *
 * @author Ryan Heaton
 */
public abstract class APTJellyTag<S extends TemplateStrategy> extends TagSupport implements TemplateBlock {

  protected final S strategy;

  protected APTJellyTag(S strategy) {
    this.strategy = strategy;
  }

  public void doTag(XMLOutput output) throws JellyTagException {
    try {
      this.strategy.invoke(this, new JellyTemplateOutput(output), getContext());
    }
    catch (IOException e) {
      throw new JellyTagException(e);
    }
    catch (MissingParameterException e) {
      throw new MissingAttributeException(e.getParameter());
    }
    catch (TemplateException e) {
      Throwable cause = e.getCause();

      if (cause instanceof JellyTagException) {
        throw (JellyTagException) cause;
      }

      throw new JellyTagException(e);
    }
  }

  /**
   * The strategy for this tag.
   *
   * @return The strategy.
   */
  public S getStrategy() {
    return strategy;
  }

  /**
   * Get the context.
   *
   * @return The context.
   */
  public APTJellyContext getContext() {
    return (APTJellyContext) super.getContext();
  }

}
