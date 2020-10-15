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
package net.sf.jelly.apt.examples;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * An example of an APF that creates a class that prints the classes and methods of a source tree.  created as an
 * example of the inefficiency of doing it this way as opposed to using apt-jelly. 
 */
public class ClassAndMethodPrinterAnnotationProcessorFactory implements AnnotationProcessorFactory {

  public Collection<String> supportedOptions() {
    return Collections.EMPTY_LIST;
  }

  public Collection<String> supportedAnnotationTypes() {
    return Collections.EMPTY_LIST;
  }

  public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> atds, AnnotationProcessorEnvironment env) {
    return new ClassAndMethodPrinterAnnotationProcessor(env);
  }

  private class ClassAndMethodPrinterAnnotationProcessor implements AnnotationProcessor {

    AnnotationProcessorEnvironment env;

    public ClassAndMethodPrinterAnnotationProcessor(AnnotationProcessorEnvironment env) {
      this.env = env;
    }

    public void process() {
      try {
        PrintWriter writer = env.getFiler().createSourceFile("net.sf.net.sf.jelly.apt.examples.ClassAndMethodPrinter");
        writer.println("package net.sf.net.sf.jelly.apt.examples;");
        writer.println();
        writer.println("public class ClassAndMethodPrinter {");
        writer.println();
        writer.println("  public static void main(String[] args) {");
        for (TypeDeclaration typeDeclaration : env.getTypeDeclarations()) {
          writer.println(String.format("    System.out.println(\"Class: %s\");", typeDeclaration.getQualifiedName()));
          for (MethodDeclaration methodDeclaration : typeDeclaration.getMethods()) {
            writer.println(String.format("    System.out.println(\"Method: %s.%s\");", typeDeclaration.getQualifiedName(), methodDeclaration.getSimpleName()));
          }
        }
        writer.println("  }");
        writer.println();
        writer.println("}");
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

}
