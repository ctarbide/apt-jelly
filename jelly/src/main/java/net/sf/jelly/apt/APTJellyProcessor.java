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
package net.sf.jelly.apt;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.XMLOutput;
import org.xml.sax.InputSource;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Annotation processor that establishes a {@link JellyContext} with the
 * {@link AnnotationProcessorEnvironment} established.  And invokes
 *
 * @author Ryan Heaton
 */
public class APTJellyProcessor implements AnnotationProcessor {

  private AnnotationProcessorEnvironment env;
  private InputSource script;

  /**
   * Construct a processor with the given environment for the given script.
   *
   * @param env The environment.
   * @param script The script.
   */
  public APTJellyProcessor(AnnotationProcessorEnvironment env, InputSource script) {
    this.env = env;
    this.script = script;
  }

  /**
   * Establish and initialize an {@link APTJellyContext} and run the script.
   */
  public void process() {
    if (env == null) {
      throw new IllegalStateException("No annotation processor environment!");
    }

    if (script == null) {
      throw new IllegalStateException("No script specified.");
    }

    APTJellyContext context = newAPTJellyContext(env);
    init(context);
    try {
      context.runScript(script, XMLOutput.createXMLOutput(System.out));
    }
    catch (Exception e) {
      StringWriter error = new StringWriter();
      PrintWriter writer = new PrintWriter(error);
      e.printStackTrace(writer);
      env.getMessager().printError(error.toString());
    }
  }

  /**
   * Instantiate a new APTJellyContext.
   *
   * @return a new APTJellyContext.
   * @param env
   */
  protected APTJellyContext newAPTJellyContext(AnnotationProcessorEnvironment env) {
    return new APTJellyContext(this.env);
  }

  /**
   * Initialize an APTJellyContext.  Default implementation sets the apt options in a variable
   * named "aptOptions".
   *
   * @param context The context to initialize.
   */
  protected void init(APTJellyContext context) {
    context.setVariable("aptOptions", this.env.getOptions());
  }

  /**
   * The AnnotationProcessorEnvironment.
   *
   * @return The AnnotationProcessorEnvironment.
   */
  public AnnotationProcessorEnvironment getAnnotationProcessorEnvironment() {
    return env;
  }

}
