package net.sf.jelly.apt.freemarker.template;

import freemarker.template.TemplateTransformModel;
import freemarker.template.TemplateModelException;

import java.io.Writer;
import java.io.IOException;
import java.util.Map;

/**
 * Evaluates its body for each {@link com.sun.mirror.declaration.TypeDeclaration type declaration}.
 * <p/>
 * Unless specified otherwise, {@link com.sun.mirror.declaration.ClassDeclaration}s will be included and {@link com.sun.mirror.declaration.InterfaceDeclaration}s will NOT be included.
 *
 * @author Ryan Heaton
 */
public class ForAllTypesTransform implements TemplateTransformModel {

  public Writer getWriter(Writer out, Map args) throws TemplateModelException, IOException {
    return null;
  }
}
