package net.sf.jelly.apt.decorations;

import junit.framework.TestCase;

/**
 * @author Ryan Heaton
 */
public class TestJavaDoc extends TestCase {

  /**
   * tests JavaDoc
   */
  public void testJavaDoc() throws Exception {
    JavaDoc javaDoc = new JavaDoc("here is some\ntext that wraps\n@tag1 with a tag\n@tag2\nand a tag that wraps.");
    assertEquals("here is some\ntext that wraps", javaDoc.toString());
    assertEquals(1, javaDoc.get("tag1").size());
    assertEquals("with a tag", javaDoc.get("tag1").get(0));
    assertEquals(1, javaDoc.get("tag2").size());
    assertEquals("and a tag that wraps.", javaDoc.get("tag2").get(0));
  }

}
