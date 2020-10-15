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
import org.xml.sax.InputSource;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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

  public APTJellyProcessorFactory() {
  }

  public APTJellyProcessorFactory(URL script) {
    super(script);
  }

  /**
   * The Jelly processor factory supports more options for backwards-compatability.
   *
   * @return The Jelly processor factory supports more options for backwards-compatability.
   */
  public Collection<String> supportedOptions() {
    Collection<String> options = new ArrayList<String>(super.supportedOptions());
    options.add(JELLY_SCRIPT_FILE_OPTION);
    options.add(JELLY_SCRIPT_URL_OPTION);
    return options;
  }

  /**
   * The Jelly processor factory supports more options for backwards-compatability.
   *
   * @return The template URL.
   */
  @Override
  protected URL getTemplateURL() {
    URL url = super.getTemplateURL();
    AnnotationProcessorEnvironment env = Context.getCurrentEnvironment();
    Map<String, String> options = env.getOptions();
    String fileOption = options.get(JELLY_SCRIPT_FILE_OPTION);

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

    return url;
  }

  //Inherited.
  protected AnnotationProcessor newProcessor(URL url) {
    return new APTJellyProcessor(Context.getCurrentEnvironment(), new InputSource(url.toString()));
  }

}
