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

import org.apache.commons.jelly.TagLibrary;

/**
 * Tag registry for the APT tags.
 *
 * @author Ryan Heaton
 */
public class APTTagLibrary extends TagLibrary {

  public APTTagLibrary() {
    registerTag("file", FileTag.class);
    registerTag("javaSource", JavaSourceTag.class);
    registerTag("forAllConstructors", ForAllConstructorsTag.class);
    registerTag("forAllFields", ForAllFieldsTag.class);
    registerTag("forAllImportedTypes", ForAllImportedTypesTag.class);
    registerTag("forAllMethods", ForAllMethodsTag.class);
    registerTag("forAllNestedTypes", ForAllNestedTypesTag.class);
    registerTag("forAllPackages", ForAllPackagesTag.class);
    registerTag("forAllParameters", ForAllParametersTag.class);
    registerTag("forAllThrownTypes", ForAllThrownTypesTag.class);
    registerTag("forAllTypes", ForAllTypesTag.class);
    registerTag("ifHasAnnotation", IfHasAnnotationTag.class);
    registerTag("ifHasDeclaration", IfHasDeclarationTag.class);
    registerTag("annotationValue", AnnotationValueTag.class);
  }

}
