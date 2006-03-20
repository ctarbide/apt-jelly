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
package net.sf.jelly.apt.decorations;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorListener;
import com.sun.mirror.apt.Filer;
import com.sun.mirror.apt.Messager;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.PackageDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.util.Declarations;
import com.sun.mirror.util.Types;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A decorated annotation processor environment returns its options correctly parsed.  See
 *
 * @author Ryan Heaton
 */
public class DecoratedAnnotationProcessorEnvironment implements AnnotationProcessorEnvironment {

  private final AnnotationProcessorEnvironment delegate;
  private final Map<String, String> options;

  public DecoratedAnnotationProcessorEnvironment(AnnotationProcessorEnvironment delegate) {
    this.delegate = delegate;
    this.options = parseOptions(delegate.getOptions());
  }

  public Map<String, String> getOptions() {
    return options;
  }

  public Messager getMessager() {
    return delegate.getMessager();
  }

  public Filer getFiler() {
    return delegate.getFiler();
  }

  public Collection<TypeDeclaration> getSpecifiedTypeDeclarations() {
    return delegate.getSpecifiedTypeDeclarations();
  }

  public PackageDeclaration getPackage(String name) {
    return delegate.getPackage(name);
  }

  public TypeDeclaration getTypeDeclaration(String name) {
    return delegate.getTypeDeclaration(name);
  }

  public Collection<TypeDeclaration> getTypeDeclarations() {
    return delegate.getTypeDeclarations();
  }

  public Collection<com.sun.mirror.declaration.Declaration> getDeclarationsAnnotatedWith(AnnotationTypeDeclaration a) {
    return delegate.getDeclarationsAnnotatedWith(a);
  }

  public Declarations getDeclarationUtils() {
    return delegate.getDeclarationUtils();
  }

  public Types getTypeUtils() {
    return delegate.getTypeUtils();
  }

  public void addListener(AnnotationProcessorListener listener) {
    delegate.addListener(listener);
  }

  public void removeListener(AnnotationProcessorListener listener) {
    delegate.removeListener(listener);
  }

  /**
   * The apt aptOptions need to be parsed because of a bug in the apt implementation.  See Sun's bug id 6258929.
   *
   * @return The *real* environment aptOptions.
   */
  protected static Map<String, String> parseOptions(Map<String,String> aptOptions) {
    HashMap<String,String> parsedOptions = new HashMap<String, String>(aptOptions.size());
    for (String key : aptOptions.keySet()) {
      String realKey = key;
      String value = aptOptions.get(key);

      int equalIndex = key.indexOf('=');
      if (equalIndex > 0) {
        //if there is an '=' in the key, it means that apt didn't parse the key correctly.  Parse the key an ignore the value.  (It'll be "null".)
        realKey = key.substring(0, equalIndex);
        value = key.substring(equalIndex + 1);
      }

      parsedOptions.put(realKey, value);
    }
    return parsedOptions;
  }

}
