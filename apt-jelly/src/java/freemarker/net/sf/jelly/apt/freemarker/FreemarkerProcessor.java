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
import freemarker.cache.URLTemplateLoader;
import freemarker.template.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.jelly.apt.freemarker.transforms.*;
import net.sf.jelly.apt.Context;

/**
 * The processor for a freemarker template file.
 *
 * @author Ryan Heaton
 */
public class FreemarkerProcessor implements AnnotationProcessor {

  private final URL templateURL;

  public FreemarkerProcessor(URL templateURL) {
    this.templateURL = templateURL;
  }

  public void process() {
    Configuration configuration = getConfiguration();

    try {
      Template template = configuration.getTemplate(templateURL.toString());
      template.process(getModel(), new OutputStreamWriter(System.out));
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    catch (TemplateException e) {
      throw new RuntimeException(e);
    }
  }

  protected APTJellyObjectWrapper getObjectWrapper() {
    return new APTJellyObjectWrapper();
  }

  /**
   * The initial data model for the template.
   *
   * @return The initial data model for the template.
   */
  protected FreemarkerModel getModel() {
    FreemarkerModel model = FreemarkerModel.get();
    model.setObjectWrapper(getObjectWrapper());
    //put the apt options into the model.
    model.put("aptOptions", Context.getCurrentEnvironment().getOptions());

    //set up the transforms....
    for (FreemarkerTransform transform : getTransforms()) {
      model.put(transform.getTransformName(), transform);
    }

    return model;
  }

  /**
   * Get the freemarker configuration.
   *
   * @return the freemarker configuration.
   */
  protected Configuration getConfiguration() {
    Configuration configuration = new Configuration();
    configuration.setTemplateLoader(getTemplateLoader());
    configuration.setLocalizedLookup(false);
    return configuration;
  }

  /**
   * The collection of transforms available to the freemarker processor.
   *
   * @return The collection of transforms available to the freemarker processor.
   */
  protected Collection<FreemarkerTransform> getTransforms() {
    Collection<FreemarkerTransform> transforms = new ArrayList<FreemarkerTransform>();
    transforms.add(new AnnotationValueTransform());
    transforms.add(new FileTransform());
    transforms.add(new ForAllConstructorsTransform());
    transforms.add(new ForAllFieldsTransform());
    transforms.add(new ForAllImportedTypesTransform());
    transforms.add(new ForAllMethodsTransform());
    transforms.add(new ForAllNestedTypesTransform());
    transforms.add(new ForAllPackagesTransform());
    transforms.add(new ForAllParametersTransform());
    transforms.add(new ForAllThrownTypesTransform());
    transforms.add(new ForAllTypesTransform());
    transforms.add(new IfHasAnnotationTransform());
    transforms.add(new IfHasDeclarationTransform());
    transforms.add(new JavaSourceTransform());
    return transforms;
  }

  /**
   * Get the template loader for the freemarker configuration.
   *
   * @return the template loader for the freemarker configuration.
   */
  protected URLTemplateLoader getTemplateLoader() {
    return new URLTemplateLoader() {
      protected URL getURL(String name) {
        try {
          return new URL(name);
        }
        catch (MalformedURLException e) {
          return null;
        }
      }
    };
  }

}
