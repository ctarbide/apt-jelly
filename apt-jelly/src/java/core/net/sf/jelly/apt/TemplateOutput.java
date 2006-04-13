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

package net.sf.jelly.apt;

import java.io.Writer;
import java.io.IOException;

/**
 * Output for a template.
 *
 * @author Ryan Heaton
 */
public interface TemplateOutput<B extends TemplateBlock> {

  /**
   * Redirect the template output to a specified writer.
   *
   * @param block The block to redirect.
   * @param writer The writer to which to redirect the output.
   */
  void redirect(B block, Writer writer) throws IOException, TemplateException;

  /**
   * Write a block of template code to the output.
   *
   * @param block The block of template code to write.
   */
  void write(B block) throws IOException, TemplateException;

  /**
   * Write a value to the template output.
   *
   * @param value The value to write.
   */
  void write(String value) throws IOException, TemplateException;

}
