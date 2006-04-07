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
package net.sf.jelly.apt.tags;

import com.sun.mirror.declaration.*;
import com.sun.mirror.type.ClassType;
import com.sun.mirror.type.InterfaceType;
import com.sun.mirror.util.Declarations;
import net.sf.jelly.apt.decorations.declaration.DecoratedDeclaration;
import org.apache.commons.jelly.JellyTagException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Loop tag through a collection of {@link MemberDeclaration}s of the {@link ForAllTypesTag#getCurrentDeclaration() current
 * type declaration}.  Options are available to include the superclasses and superinterfaces for
 * the current type declaration.  By default, the members of superclasses and superinterfaces
 * will not be looped through.
 *
 * @author Ryan Heaton
 */
public abstract class MemberDeclarationLoopTag<M extends MemberDeclaration> extends AnnotationFilterableDeclarationLoopTag<M> {

  private boolean includeSuperclasses = false;
  private boolean includeSuperinterfaces = false;
  private TypeDeclaration declaration;

  public Collection<M> getAllDeclarationsToConsiderForAnnotationFiltering() throws JellyTagException {
    ArrayList<M> allDeclarations = new ArrayList<M>();
    TypeDeclaration typeDeclaration = getDeclaration();
    if (typeDeclaration == null) {
      typeDeclaration = getCurrentTypeDeclaration();

      if (typeDeclaration == null) {
        throw new JellyTagException("The loop tag for member declarations must either be within a loop tag for type declarations the declaration must be specified.");
      }
    }

    allDeclarations.addAll(getMemberDeclarations(typeDeclaration));

    if ((includeSuperclasses) && (typeDeclaration instanceof ClassDeclaration)) {
      Collection<M> superclassDeclarations = getSuperclassesMemberDeclarations((ClassDeclaration) typeDeclaration);
      for (M declaration : superclassDeclarations) {
        if (!hidden(declaration, allDeclarations)) {
          allDeclarations.add(declaration);
        }
      }
    }

    if (includeSuperinterfaces) {
      Collection<M> superinterfaceDeclarations = getSuperinterfacesMemberDeclarations(typeDeclaration);
      for (M declaration : superinterfaceDeclarations) {
        if (!hidden(declaration, allDeclarations)) {
          allDeclarations.add(declaration);
        }
      }
    }

    return allDeclarations;
  }

  /**
   * Determines whether a declaration is hidden by any declaration in the given list.
   * @param declaration The declaration.
   * @param declarations The declaration list.
   * @return Whether a declaration is hidden by any declaration in the given list.
   */
  @SuppressWarnings({"unchecked"})
  protected boolean hidden(M declaration, ArrayList<M> declarations) {
    if (declaration instanceof DecoratedDeclaration) {
      declaration = (M) ((DecoratedDeclaration) declaration).getDelegate();
    }
    boolean hides = false;
    Declarations declarationUtils = getAnnotationProcessorEnvironment().getDeclarationUtils();
    for (M sub : declarations) {
      if (sub instanceof DecoratedDeclaration) {
        sub = (M) ((DecoratedDeclaration) sub).getDelegate();
      }

      hides |= declarationUtils.hides(sub, declaration);
      if ((declaration instanceof MethodDeclaration) && (sub instanceof MethodDeclaration)) {
        hides |= declarationUtils.overrides((MethodDeclaration) sub, (MethodDeclaration) declaration);
      }
    }
    return hides;
  }

  /**
   * Get the member declarations for all superclasses of <code>type</code>.
   *
   * @param type The class declaration for which to get the member declarations of all superclasses.
   * @return The member declarations.
   */
  protected Collection<M> getSuperclassesMemberDeclarations(ClassDeclaration type) throws JellyTagException {
    ArrayList<M> declarations = new ArrayList<M>();

    ClassType superclass = type.getSuperclass();
    if (superclass != null) {
      ClassDeclaration declaration = superclass.getDeclaration();
      if (declaration != null) {
        declarations.addAll(getMemberDeclarations(declaration));
        declarations.addAll(getSuperclassesMemberDeclarations(declaration));
      }
    }

    return declarations;
  }

  /**
   * Get the member declarations for all superinterfaces of <code>type</code>.
   *
   * @param type The class declaration for which to get the member declarations of all superinterfaces.
   * @return The member declarations.
   */
  protected Collection<M> getSuperinterfacesMemberDeclarations(TypeDeclaration type) throws JellyTagException {
    ArrayList<M> declarations = new ArrayList<M>();
    Collection<InterfaceType> superinterfaces = type.getSuperinterfaces();
    for (InterfaceType superinterface : superinterfaces) {
      InterfaceDeclaration declaration = superinterface.getDeclaration();
      if (declaration != null) {
        declarations.addAll(getMemberDeclarations(declaration));
        declarations.addAll(getSuperinterfacesMemberDeclarations(declaration));
      }
    }

    return declarations;
  }

  /**
   * Get the desired member declarations of a given declaration.
   *
   * @param declaration The declaration.
   * @return the desired member declarations of a given declaration.
   */
  protected abstract Collection<M> getMemberDeclarations(TypeDeclaration declaration) throws JellyTagException;

  /**
   * Get the {@link ForAllTypesTag#getCurrentDeclaration() current type declaration}
   *
   * @return The current type declaration.
   * @throws JellyTagException If this tag isn't in a type declaration loop.
   */
  protected TypeDeclaration getCurrentTypeDeclaration() throws JellyTagException {
    ForAllTypesTag tag = (ForAllTypesTag) findAncestorWithClass(ForAllTypesTag.class);
    return ((tag == null) ? null : tag.getCurrentDeclaration());
  }

  /**
   * Whether to include superclasses.
   *
   * @return Whether to include superclasses.
   */
  public boolean isIncludeSuperclasses() {
    return includeSuperclasses;
  }

  /**
   * Whether to include superclasses.
   *
   * @param includeSuperclasses Whether to include superclasses.
   */
  public void setIncludeSuperclasses(boolean includeSuperclasses) {
    this.includeSuperclasses = includeSuperclasses;
  }

  /**
   * Whether to include superinterfaces.
   *
   * @return Whether to include superinterfaces.
   */
  public boolean isIncludeSuperinterfaces() {
    return includeSuperinterfaces;
  }

  /**
   * Whether to include superinterfaces.
   *
   * @param includeSuperinterfaces Whether to include superinterfaces.
   */
  public void setIncludeSuperinterfaces(boolean includeSuperinterfaces) {
    this.includeSuperinterfaces = includeSuperinterfaces;
  }

  /**
   * The specified declaration.
   *
   * @return The specified declaration.
   */
  public TypeDeclaration getDeclaration() {
    return declaration;
  }

  /**
   * The specified declaration.
   *
   * @param declaration The specified declaration.
   */
  public void setDeclaration(TypeDeclaration declaration) {
    this.declaration = declaration;
  }
}
