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

import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessors;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

import java.util.*;

import net.sf.jelly.apt.decorations.DecoratedAnnotationProcessorEnvironment;

/**
 * Base class for the annotation processor factory.
 *
 * @author Ryan Heaton
 */
public abstract class ProcessorFactory implements AnnotationProcessorFactory {

  protected static int round = 0;

  /**
   * By default, there are no supported options.
   *
   * @return An empty collection of options.
   */
  public Collection<String> supportedOptions() {
    return new ArrayList<String>();
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

    Context.setCurrentEnvironment(decorateEnvironment(env));

    return getProcessorFor(set);
  }

  /**
   * Gets the processor for the given set of annotations.
   *
   * @param annotations The annotations.
   * @return The processor.
   */
  protected abstract AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> annotations);

  /**
   * Decorate the annotation processor environment.
   *
   * @param env The environment to decorate.
   * @return The decorated environment.
   */
  protected AnnotationProcessorEnvironment decorateEnvironment(AnnotationProcessorEnvironment env) {
    return new DecoratedAnnotationProcessorEnvironment(env);
  }

}
