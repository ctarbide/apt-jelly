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

import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.XMLOutput;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.Writer;

/**
 * Output implementation for jelly templates.
 *
 * @author Ryan Heaton
 */
public class JellyTemplateOutput implements TemplateOutput<APTJellyTag> {

  private final XMLOutput output;

  public JellyTemplateOutput(XMLOutput output) {
    this.output = output;
  }

  //Inherited.
  public void redirect(APTJellyTag block, Writer writer) throws IOException, TemplateException {
    try {
      block.invokeBody(XMLOutput.createXMLOutput(writer));
    }
    catch (JellyTagException e) {
      throw new TemplateException(e);
    }
  }

  //Inherited.
  public void write(APTJellyTag block) throws IOException, TemplateException {
    try {
      block.invokeBody(this.output);
    }
    catch (JellyTagException e) {
      throw new TemplateException(e);
    }
  }

  //Inherited.
  public void write(String value) throws IOException, TemplateException {
    try {
      this.output.write(value);
    }
    catch (SAXException e) {
      throw new TemplateException(e);
    }
  }

}
