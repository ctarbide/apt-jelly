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

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import net.sf.jelly.apt.Context;
import net.sf.jelly.apt.ProcessorFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * A processor factory for the freemarker engine.
 *
 * @author Ryan Heaton
 */
public class FreemarkerProcessorFactory extends ProcessorFactory {

  /**
   * Option to pass to APT specifying the freemarker template file on the filesystem to invoke.
   */
  public static final String TEMPLATE_FILE_OPTION = "-Atemplate";

  /**
   * Option to pass to APT specifying a URL of the freemarker template to invoke.
   */
  public static final String TEMPLATE_URL_OPTION = "-AtemplateURL";

  private static final Collection<String> SUPPORTED_OPTIONS = Collections.unmodifiableCollection(Arrays.asList(TEMPLATE_FILE_OPTION, TEMPLATE_URL_OPTION));

  private URL script;

  @Override
  public Collection<String> supportedOptions() {
    return SUPPORTED_OPTIONS;
  }

  public FreemarkerProcessorFactory() {
    this.script = null;
  }

  public FreemarkerProcessorFactory(URL script) {
    this.script = script;
  }

  /**
   * Get a freemarker processor.
   *
   * @param annotations The annotations.
   * @return The freemarker processor.
   */
  protected AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> annotations) {
    AnnotationProcessorEnvironment env = Context.getCurrentEnvironment();
    Map<String, String> options = env.getOptions();
    String fileOption = options.get(TEMPLATE_FILE_OPTION);
    URL url = this.script;
    if ((url == null) && (fileOption != null)) {
      try {
        url = new File(fileOption).toURL();
      }
      catch (MalformedURLException e) {
        env.getMessager().printError("Bad file: " + fileOption);
      }
    }

    if (url == null) {
      String urlOption = options.get(TEMPLATE_URL_OPTION);

      if (urlOption != null) {
        try {
          url = new URL(urlOption);
        }
        catch (MalformedURLException e) {
          env.getMessager().printError("Bad url: " + urlOption);
        }
      }
    }

    if (url == null) {
      throw new IllegalArgumentException(String.format("A valid script option (%s or %s) must be set.", TEMPLATE_FILE_OPTION, TEMPLATE_URL_OPTION));
    }

    return newFreemarkerProcessor(url);
  }

  /**
   * Instantiate a new freemarker processor.
   *
   * @param url The URL to the template.
   * @return The processor.
   */
  protected AnnotationProcessor newFreemarkerProcessor(URL url) {
    return new FreemarkerProcessor(url);
  }

}
