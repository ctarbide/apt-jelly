package net.sf.jelly.apt.decorations;

import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.type.InterfaceType;
import net.sf.jelly.apt.util.JavaDocTagHandler;
import net.sf.jelly.apt.util.NoOpTagHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * JavaDoc for a method can inherit certain values.
 *
 * @author Ryan Heaton
 */
public class MethodJavaDoc extends JavaDoc {

  private final MethodDeclaration method;

  public MethodJavaDoc(MethodDeclaration method, String docComment, JavaDocTagHandler handler) {
    super(docComment, handler);
    this.method = method;
    super.init(docComment, handler);
    throw new UnsupportedOperationException("Not fully implemented yet.");
  }

  @Override
  protected void init(String docComment, JavaDocTagHandler tagHandler) {
    //no-op...
  }

  @Override
  protected boolean doTagHandling(JavaDocTagHandler tagHandler) {
    return true;
  }

  @Override
  protected String handleAllTags(final String tag, final String value, final JavaDocTagHandler handler) {
    String tagSubname = null;
    if ("param".equals(tag) || "throws".equals(tag) || "exception".equals(tag)) {
      String tagValue = String.valueOf(value).replaceAll("\\s", " ");
      int spaceIndex = tagValue.indexOf(' ');
      if (spaceIndex == -1) {
        spaceIndex = tagValue.length();
      }

      tagSubname = tagValue.substring(0, spaceIndex);
    }
    final String subname = tagSubname;

    String newValue = super.handleAllTags(tag, value, new JavaDocTagHandler() {
      public Object onInlineTag(String tagName, String tagText) {
        if ("inheritDoc".equals(tagName)) {
          return findInheritedDoc(tag, subname);
        }
        else {
          return handler == null ? null : handler.onInlineTag(tagName, tagText);
        }
      }

      public Object onMarkupTag(String tagName, String tagBody) {
        return handler == null ? null : handler.onMarkupTag(tagName, tagBody);
      }
    });

    if (newValue == null || "".equals(newValue)) {
      newValue = findInheritedDoc(tag, subname);
    }

    return newValue;
  }

  private String findInheritedDoc(String tagName, String tagSubname) {
    return null;
  }

}
