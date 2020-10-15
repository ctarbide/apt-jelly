package net.sf.jelly.apt.freemarker.transforms;

import net.sf.jelly.apt.freemarker.FreemarkerTransform;
import net.sf.jelly.apt.strategies.TypeDeclarationLoopStrategy;

/**
 * Evaluates its body for each {@link com.sun.mirror.declaration.TypeDeclaration type declaration}.
 * <p/>
 * If the package is specified, only the top-level classes are returned.
 * <p/>
 * Unless specified otherwise, {@link com.sun.mirror.declaration.ClassDeclaration}s will be included and {@link com.sun.mirror.declaration.InterfaceDeclaration}s will NOT be included.
 *
 * @author Ryan Heaton
 */
public class ForAllTypesTransform extends FreemarkerTransform<TypeDeclarationLoopStrategy> {

  /**
   * Construct a new transform under the specified namespace.  <code>null</code> or <code>""</code> means the root namespace.
   *
   * @param namespace The namespace.
   */
  public ForAllTypesTransform(String namespace) {
    super(namespace);
  }

  // Inherited.
  public TypeDeclarationLoopStrategy newStrategy() {
    return new TypeDeclarationLoopStrategy();
  }

}
