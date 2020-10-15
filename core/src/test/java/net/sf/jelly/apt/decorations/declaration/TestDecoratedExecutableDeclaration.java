package net.sf.jelly.apt.decorations.declaration;

import junit.framework.TestCase;
import net.sf.jelly.apt.decorations.JavaDoc;

import java.util.HashMap;

/**
 * @author Ryan Heaton
 */
public class TestDecoratedExecutableDeclaration extends TestCase {

  /**
   * tests parsing param comments
   */
  public void testParseParamComments() throws Exception {
    JavaDoc javaDoc = new JavaDoc("here is a method\n@param param1 param1 comment\n@param param2\nparam2 comment");
    HashMap<String,String> map = DecoratedExecutableDeclaration.parseParamComments(javaDoc);
    assertEquals("param1 comment", map.get("param1"));
    assertEquals("param2 comment", map.get("param2"));
  }
}
