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

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.apt.AnnotationProcessors;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import net.sf.jelly.apt.decorations.DeclarationDecorator;
import net.sf.jelly.apt.decorations.DecoratedAnnotationProcessorEnvironment;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Base class for the annotation processor factory.
 *
 * @author Ryan Heaton
 */
public abstract class ProcessorFactory implements AnnotationProcessorFactory {

  /**
   * Option to pass to APT specifying the template file on the filesystem to invoke.
   */
  public static final String TEMPLATE_FILE_OPTION = "-Atemplate";

  /**
   * Option to pass to APT specifying a URL of the template to invoke.
   */
  public static final String TEMPLATE_URL_OPTION = "-AtemplateURL";

  /**
   * Option for specifying the declaration decorator.
   */
  public static final String DECLARATION_DECORATOR_OPTION = "-A" + DeclarationDecorator.class.getName();

  /**
   * Option for specifying the type decorator.
   */
  public static final String TYPE_DECORATOR_OPTION = "-A" + TypeMirrorDecorator.class.getName();

  private static final Collection<String> SUPPORTED_OPTIONS = Collections.unmodifiableCollection(Arrays.asList(TEMPLATE_FILE_OPTION,
                                                                                                               TEMPLATE_URL_OPTION,
                                                                                                               DECLARATION_DECORATOR_OPTION,
                                                                                                               TYPE_DECORATOR_OPTION));
  protected static int round = 0;
  protected URL template;

  protected ProcessorFactory() {
    this.template = null;
  }

  protected ProcessorFactory(URL template) {
    this.template = template;
  }

  /**
   * The supported options for this factory.
   *
   * @return The supported options for this factory.
   */
  public Collection<String> supportedOptions() {
    return SUPPORTED_OPTIONS;
  }

  /**
   * By default, all annotation types are supported.
   *
   * @return The wildcard ("*").
   */
  public Collection<String> supportedAnnotationTypes() {
    return Collections.unmodifiableCollection(Arrays.asList("*"));
  }

  /**
   * Default implementation ensures only one APT round does any work and establishes the context before instantiating the processor.
   *
   * @param set The relevant annotation types.
   * @param env The environment.
   * @return The processor.
   */
  public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> set, AnnotationProcessorEnvironment env) {
    if (++round > 1) {
      //we only process one round...
      //todo: think of some way to process more than one round?
      return AnnotationProcessors.NO_OP;
    }

    AnnotationProcessorEnvironment ape = decorateEnvironment(env);

    if (ape.getOptions().containsKey(DECLARATION_DECORATOR_OPTION)) {
      System.setProperty(DeclarationDecorator.class.getName(), ape.getOptions().get(DECLARATION_DECORATOR_OPTION));
    }

    if (ape.getOptions().containsKey(TYPE_DECORATOR_OPTION)) {
      System.setProperty(TypeMirrorDecorator.class.getName(), ape.getOptions().get(TYPE_DECORATOR_OPTION));
    }

    Context.setCurrentEnvironment(ape);

    return getProcessorFor(set);
  }

  /**
   * Get a freemarker processor.
   *
   * @param annotations The annotations.
   * @return The freemarker processor.
   */
  protected AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> annotations) {
    URL url = getTemplateURL();

    if (url == null) {
      throw new IllegalArgumentException(String.format("A valid template option (%s or %s) must be set.", TEMPLATE_FILE_OPTION, TEMPLATE_URL_OPTION));
    }

    return newProcessor(url);
  }

  /**
   * Decorate the annotation processor environment.
   *
   * @param env The environment to decorate.
   * @return The decorated environment.
   */
  protected AnnotationProcessorEnvironment decorateEnvironment(AnnotationProcessorEnvironment env) {
    return new DecoratedAnnotationProcessorEnvironment(env);
  }

  /**
   * Get the URL to the template.
   *
   * @return The URL to the template.
   */
  protected URL getTemplateURL() {
    AnnotationProcessorEnvironment env = Context.getCurrentEnvironment();
    Map<String, String> options = env.getOptions();
    String fileOption = options.get(TEMPLATE_FILE_OPTION);
    URL url = this.template;
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
    return url;
  }

  /**
   * Instantiate a new processor.
   *
   * @param url The URL to the template.
   * @return The processor.
   */
  protected abstract AnnotationProcessor newProcessor(URL url);
}
