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

import freemarker.ext.beans.BeanModel;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateTransformModel;
import net.sf.jelly.apt.strategies.TemplateStrategyControl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Base class for freemarker transforms.
 *
 * @author Ryan Heaton
 */
public abstract class FreemarkerTransform<S extends TemplateStrategyControl> implements TemplateTransformModel {

  //Inherited.
  public Writer getWriter(Writer out, Map args) throws TemplateModelException, IOException {
    S strategy = newStrategy();
    setupStrategy(strategy, args);
    return new FreemarkerTemplateBlock(out, strategy, getTransformName());
  }

  /**
   * Setup the strategy given the specified arguments to the template transform.
   *
   * @param strategy The strategy to set up.
   * @param args The arguments to the transform.
   */
  protected void setupStrategy(S strategy, Map args) throws TemplateModelException {
    //set up the strategy
    for (Object key : args.keySet()) {
      String property = (String) key;
      Object value = args.get(property);

      value = unwrap(value, property);

      Method method = findSetter(property, strategy);
      try {
        method.invoke(strategy, value);
      }
      catch (IllegalArgumentException e) {
        throw new TemplateModelException("The '" + getTransformName() + "' transform doesn't support the '" + property + "' attribute");
      }
      catch (Exception e) {
        throw new TemplateModelException(e);
      }
    }
  }

  /**
   * Unwrap the specified value of the given property.
   *
   * @param value The value to unwrap.
   * @param property The property.
   * @return The unwrapped object.
   */
  protected Object unwrap(Object value, String property) throws TemplateModelException {
    //unwrap the value.
    if (value instanceof BeanModel) {
      value = ((BeanModel) value).getWrappedObject();
    }
    else if (value instanceof TemplateBooleanModel) {
      value = ((TemplateBooleanModel) value).getAsBoolean();
    }
    else if (value != null) {
      throw new TemplateModelException("Unsupported value for parameter '" + property + "' on transform '" + getTransformName() + "': " +
        value.getClass().getName());
    }
    return value;
  }

  /**
   * Finds the setter for the given property on the specified strategy.
   *
   * @param property The property.
   * @param strategy The strategy.
   * @return The setter, or null if not found.
   */
  protected Method findSetter(String property, S strategy) throws TemplateModelException {
    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(strategy.getClass());
      for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
        if (descriptor.getName().equals(property)) {
          return descriptor.getWriteMethod();
        }
      }
    }
    catch (IntrospectionException e) {
      //fall through....
    }

    throw new TemplateModelException("The '" + getTransformName() + "' transform doesn't support the '" + property + "' attribute");
  }

  /**
   * Instantiate a new strategy instance.
   *
   * @return The strategy.
   */
  public abstract S newStrategy();

  /**
   * The name of the transform.
   *
   * @return The name of the transform.
   */
  public abstract String getTransformName();

}
