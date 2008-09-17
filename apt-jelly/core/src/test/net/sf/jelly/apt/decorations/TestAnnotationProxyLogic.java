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

package net.sf.jelly.apt.decorations;

import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import net.sf.jelly.apt.InAPTTestCase;
import net.sf.jelly.apt.decorations.declaration.DecoratedClassDeclaration;

/**
 * @author Ryan Heaton
 */
public class TestAnnotationProxyLogic extends InAPTTestCase {

  /**
   * Tests getting the annotation values from MegaAnnotation.
   */
  public void testMegaAnnotation() throws Exception {
    TypeDeclaration declaration = new DecoratedClassDeclaration((ClassDeclaration) getDeclaration("net.sf.jelly.apt.decorations.MegaAnnotatedClass"));
    MegaAnnotation annotation = declaration.getAnnotation(MegaAnnotation.class);
    assertEquals(false, annotation.boolElement());
    assertEquals(0, annotation.intElementWithDefault());
    assertEquals(3, annotation.intElement());
    assertEquals("hi", annotation.stringElement());
    assertEquals("hello", annotation.stringElementWithDefault());
    assertSame(TestAnnotationProxyLogic.class, annotation.classElement());
    assertSame(MegaAnnotation.DEFAULT.class, annotation.classElementWithDefault());
    assertEquals("singleValue", annotation.depAnnotation().value());
    assertEquals(2, annotation.depAnnotations().length);
    assertEquals("multiValue1", annotation.depAnnotations()[0].value());
    assertEquals("multiValue2", annotation.depAnnotations()[1].value());
  }
}
