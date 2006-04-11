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
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import org.xml.sax.InputSource;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Basic annotation processor factory for a APTJellyProcessor.
 *
 * @author Ryan Heaton
 */
public class APTJellyProcessorFactory extends ProcessorFactory {

  /**
   * Option to pass to APT specifying the jelly script on the filesystem to invoke.
   */
  public static final String JELLY_SCRIPT_FILE_OPTION = "-AjellyScript";

  /**
   * Option to pass to APT specifying a URL of the jelly script to invoke.
   */
  public static final String JELLY_SCRIPT_URL_OPTION = "-AjellyScriptURL";

  private static final Collection<String> SUPPORTED_OPTIONS = Collections.unmodifiableCollection(Arrays.asList(JELLY_SCRIPT_FILE_OPTION, JELLY_SCRIPT_URL_OPTION));
  private static final Collection<String> SUPPORTED_TYPES = Collections.unmodifiableCollection(Arrays.asList("*"));

  private URL script;
  protected static int round = 0;

  public APTJellyProcessorFactory() {
    script = null;
  }

  public APTJellyProcessorFactory(URL script) {
    this.script = script;
  }

  public Collection<String> supportedOptions() {
    return SUPPORTED_OPTIONS;
  }

  public Collection<String> supportedAnnotationTypes() {
    return SUPPORTED_TYPES;
  }

  protected AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> annotations) {
    AnnotationProcessorEnvironment env = Context.getCurrentEnvironment();
    Map<String, String> options = env.getOptions();
    String fileOption = options.get(JELLY_SCRIPT_FILE_OPTION);
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
      String urlOption = options.get(JELLY_SCRIPT_URL_OPTION);

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
      throw new IllegalArgumentException(String.format("A valid script option (%s or %s) must be set.", JELLY_SCRIPT_FILE_OPTION, JELLY_SCRIPT_URL_OPTION));
    }

    InputSource source = new InputSource(url.toString());
    return newJellyContextAnnotationProcessor(env, source);
  }

  /**
   * Create a new {@link APTJellyProcessor} given the environment and script.
   * 
   * @param env The AnnotationProcessorEnvironment.
   * @param script The source for the script.
   * @return The processor.
   */
  protected APTJellyProcessor newJellyContextAnnotationProcessor(AnnotationProcessorEnvironment env, InputSource script) {
    return new APTJellyProcessor(env, script);
  }

}
