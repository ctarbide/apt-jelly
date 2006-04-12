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
import com.sun.mirror.type.AnnotationType;
import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.MissingAttributeException;
import org.apache.commons.jelly.XMLOutput;
import org.xml.sax.SAXException;

/**
 * Outputs an annotation value.  If a declaration is specified, it will be used to lookup the annotation value.
 * Otherwise, this tag must be nested within a declaration loop tag and the current declaration will be used.  If
 * a default value is specified, it will be output if either (1) the specified annotation doesn't exist, (2) the
 * specified annotation element doesn't exist, or (3) the value of the specified annotation element is its default
 * value.  If no default value is specified, the declared default value of the element will be output (if there is one).
 * Otherwise, nothing will be output.
 *
 * @author Ryan Heaton
 */
public class AnnotationValueTag extends JellyTagSupport {

  private Declaration declaration;
  private String defaultValue;
  private String annotation;
  private String element;

  public void doTag(XMLOutput xmlOutput) throws MissingAttributeException, JellyTagException {
    Declaration declaration = getDeclaration();
    if (declaration == null) {
      declaration = getCurrentDeclaration();

      if (declaration == null) {
        throw new JellyTagException("The annotation value tag must either be nested withing a declaration loop or the declaration must be specified.");
      }
    }

    if (annotation == null) {
      throw new MissingAttributeException("annotation");
    }

    String element = this.element;
    if (element == null) {
      element = "value";
    }

    Object output = this.defaultValue; //use the specified default value if all else fails.
    ANNOTATION_MIRROR_LOOP :
    for (AnnotationMirror annotationMirror : declaration.getAnnotationMirrors()) {
      AnnotationType annotationType = annotationMirror.getAnnotationType();
      if (annotationType != null) {
        AnnotationTypeDeclaration annotationTypeDeclaration = annotationType.getDeclaration();
        if ((annotationTypeDeclaration != null) && (annotation.equals(annotationTypeDeclaration.getQualifiedName()))) {
          //find the element value...
          for (AnnotationTypeElementDeclaration elementDeclaration : annotationTypeDeclaration.getMethods()) {
            if (element.equals(elementDeclaration.getSimpleName())) {
              //now we can calculate the output...
              AnnotationValue annotationValue = annotationMirror.getElementValues().get(elementDeclaration);
              AnnotationValue defaultValue = elementDeclaration.getDefaultValue();

              if (annotationValue != null) {
                output = annotationValue.getValue();
              }
              else if (this.defaultValue == null) {
                //only use the default value if a default value isn't specified in the tag.
                output = defaultValue.getValue();
              }

              break ANNOTATION_MIRROR_LOOP;
            }
          }
        }
      }
    }

    if (output != null) {
      try {
        xmlOutput.write(output.toString());
      }
      catch (SAXException e) {
        throw new JellyTagException(e);
      }
    }
  }

  /**
   * Get the {@link DeclarationLoopTag#getCurrentDeclaration() current declaration}
   *
   * @return The current declaration.
   * @throws JellyTagException If this tag isn't in a declaration loop.
   */
  protected Declaration getCurrentDeclaration() throws JellyTagException {
    DeclarationLoopTag tag = (DeclarationLoopTag) findAncestorWithClass(DeclarationLoopTag.class);
    return ((tag == null) ? null : tag.getCurrentDeclaration());
  }

  /**
   * The declaration on which to get the annotation value.
   *
   * @return The declaration on which to get the annotation value.
   */
  public Declaration getDeclaration() {
    return declaration;
  }

  /**
   * The declaration on which to get the annotation value.
   *
   * @param declaration The declaration on which to get the annotation value.
   */
  public void setDeclaration(Declaration declaration) {
    this.declaration = declaration;
  }

  /**
   * The default value to output under any of the following circumstances:
   * <p/>
   * <ul>
   * <li>the annotation wasn't found</li>
   * <li>the specified type element wasn't found</li>
   * <li>the value of the specified type element was its default value.</li>
   * </ul>
   *
   * @return The default value to output.
   */
  public String getDefault() {
    return defaultValue;
  }

  /**
   * The default to return under any of the following circumstances:
   * <p/>
   * <ul>
   * <li>the annotation wasn't found</li>
   * <li>the specified type element wasn't found</li>
   * <li>the value of the specified type element was its default value.</li>
   * </ul>
   *
   * @param defaultValue The default value.
   */
  public void setDefault(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  /**
   * The specified annotation.
   *
   * @return The specified annotation.
   */
  public String getAnnotation() {
    return annotation;
  }

  /**
   * The specified annotation.
   *
   * @param annotation The specified annotation.
   */
  public void setAnnotation(String annotation) {
    this.annotation = annotation;
  }

  /**
   * The type element.  Default is "value".
   *
   * @return The type element.  Default is "value".
   */
  public String getElement() {
    return element;
  }

  /**
   * The type element.  Default is "value".
   *
   * @param element The type element.  Default is "value".
   */
  public void setElement(String element) {
    this.element = element;
  }
}
