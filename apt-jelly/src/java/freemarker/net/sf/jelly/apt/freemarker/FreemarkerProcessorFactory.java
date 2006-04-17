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

import net.sf.jelly.apt.ProcessorFactory;
import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

import java.util.Set;

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

  protected AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> annotations) {
    return null;
  }

}
