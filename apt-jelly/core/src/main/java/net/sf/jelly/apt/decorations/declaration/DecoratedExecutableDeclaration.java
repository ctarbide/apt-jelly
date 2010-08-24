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
package net.sf.jelly.apt.decorations.declaration;

import com.sun.mirror.declaration.ExecutableDeclaration;
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.declaration.TypeParameterDeclaration;
import com.sun.mirror.type.ReferenceType;
import com.sun.mirror.util.DeclarationVisitor;
import net.sf.jelly.apt.decorations.DeclarationDecorator;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;
import net.sf.jelly.apt.decorations.JavaDoc;
import net.sf.jelly.apt.decorations.type.DecoratedReferenceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Ryan Heaton
 */
public class DecoratedExecutableDeclaration extends DecoratedMemberDeclaration implements ExecutableDeclaration {

  protected HashMap<String, String> throwsComments;
  protected HashMap<String, String> paramsComments;

  public DecoratedExecutableDeclaration(ExecutableDeclaration delegate) {
    super(delegate);

    throwsComments = new HashMap<String, String>();
    ArrayList<String> allThrowsComments = new ArrayList<String>();
    if (getJavaDoc().get("throws") != null) {
      allThrowsComments.addAll(getJavaDoc().get("throws"));
    }

    if (getJavaDoc().get("exception") != null) {
      allThrowsComments.addAll(getJavaDoc().get("exception"));
    }

    for (String throwsDoc : allThrowsComments) {
      int spaceIndex = throwsDoc.indexOf(' ');
      if (spaceIndex == -1) {
        spaceIndex = throwsDoc.length();
      }

      String exception = throwsDoc.substring(0, spaceIndex);
      String throwsComment = "";
      if ((spaceIndex + 1) < throwsDoc.length()) {
        throwsComment = throwsDoc.substring(spaceIndex + 1);
      }

      throwsComments.put(exception, throwsComment);
    }

    this.paramsComments = parseParamComments(getJavaDoc());
  }

  protected static HashMap<String, String> parseParamComments(JavaDoc jd) {
    HashMap<String, String> paramComments = new HashMap<String, String>();
    if (jd.get("param") != null) {
      for (String paramDoc : jd.get("param")) {
        paramDoc = paramDoc.replaceAll("\\s", " ");
        int spaceIndex = paramDoc.indexOf(' ');
        if (spaceIndex == -1) {
          spaceIndex = paramDoc.length();
        }

        String param = paramDoc.substring(0, spaceIndex);
        String paramComment = "";
        if ((spaceIndex + 1) < paramDoc.length()) {
          paramComment = paramDoc.substring(spaceIndex + 1);
        }

        paramComments.put(param, paramComment);
      }
    }
    return paramComments;
  }

  public boolean isVarArgs() {
    return ((ExecutableDeclaration)delegate).isVarArgs();
  }

  //Inherited.
  public void accept(DeclarationVisitor v) {
    v.visitExecutableDeclaration(this);
  }

  public Collection<TypeParameterDeclaration> getFormalTypeParameters() {
    return DeclarationDecorator.decorate(((ExecutableDeclaration)delegate).getFormalTypeParameters());
  }

  public Collection<ParameterDeclaration> getParameters() {
    Collection<ParameterDeclaration> params = DeclarationDecorator.decorate(((ExecutableDeclaration) delegate).getParameters());
    for (ParameterDeclaration param : params) {
      if (paramsComments.get(param.getSimpleName()) != null) {
        ((DecoratedParameterDeclaration)param).setDocComment(paramsComments.get(param.getSimpleName()));
      }
    }
    return params;
  }

  public Collection<ReferenceType> getThrownTypes() {
    Collection<ReferenceType> thrownTypes = TypeMirrorDecorator.decorate(((ExecutableDeclaration) delegate).getThrownTypes());

    for (ReferenceType thrownType : thrownTypes) {
      String fullyQualifiedThrownTypeName = String.valueOf(thrownType);
      String throwsComment = throwsComments.get(fullyQualifiedThrownTypeName);
      if (throwsComment == null) {
        //try keying off the simple name in case that is how it is referenced in the javadocs.
        throwsComment = throwsComments.get(fullyQualifiedThrownTypeName.substring(fullyQualifiedThrownTypeName.lastIndexOf('.') + 1));
      }
      ((DecoratedReferenceType)thrownType).setDocComment(throwsComment);
    }

    return thrownTypes;
  }

}
