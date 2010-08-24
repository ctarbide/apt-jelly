package net.sf.jelly.apt.decorations;

import junit.framework.TestCase;

import java.util.regex.Matcher;

import net.sf.jelly.apt.util.NoOpTagHandler;
import net.sf.jelly.apt.util.BasicInlineTagStrippingHandler;

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

  /**
   *
   */
  public void testRegexpMatching() throws Exception {
    Matcher matcher = JavaDoc.INLINE_TAG_PATTERN.matcher("{@this} is some text that has {@link some inline tags}.");
    assertTrue(matcher.find());
    assertEquals("this", matcher.group(1));
    assertEquals("", matcher.group(2));
    assertTrue(matcher.find());
    assertEquals("link", matcher.group(1));
    assertEquals("some inline tags", matcher.group(2));

    matcher = JavaDoc.MARKUP_TAG_PATTERN.matcher("<b>this</b> is some text that has <i>some markup tags</i>.");
    assertTrue(matcher.find());
    assertEquals("b", matcher.group(1));
    assertEquals("this", matcher.group(2));
    assertTrue(matcher.find());
    assertEquals("i", matcher.group(1));
    assertEquals("some markup tags", matcher.group(2));
  }

  /**
   * tests tag handling.
   */
  public void testTagHandling() throws Exception {
    JavaDoc javaDoc = new JavaDoc();
    String value = javaDoc.handleAllTags(null, "some text {@link with a link} and <b>some markup <i>with other tags</i> in it.</b><br/>", new NoOpTagHandler());
    assertEquals("some text {@link with a link} and <b>some markup <i>with other tags</i> in it.</b><br/>", value);
    value = javaDoc.handleAllTags(null, "some text {@link with a link} and <b>some markup <i>with other tags</i> in it.</b><br/>", new BasicInlineTagStrippingHandler());
    assertEquals("some text with a link and <b>some markup <i>with other tags</i> in it.</b><br/>", value);
    value = javaDoc.handleAllTags(null, "some text {@link with a link} and <b and=\"attributes\" such=\"as\">some markup <i>with other tags</i> in it.</b><br/>", new BasicInlineTagStrippingHandler() {
      @Override
      public String onMarkupTag(String tagName, String tagBody) {
        return tagBody;
      }
    });
    assertEquals("some text with a link and some markup <i>with other tags</i> in it.<br/>", value);
    value = javaDoc.handleAllTags(null, "some text {@link with a link} and <b>some markup <i>with other tags</i> in it.</b><br/>", new BasicInlineTagStrippingHandler() {
      @Override
      public Object onMarkupTag(String tagName, final String tagBody) {
        return new TextToBeHandled() {
          @Override
          public String toString() {
            return tagBody;
          }
        };
      }
    });
    assertEquals("some text with a link and some markup with other tags in it.<br/>", value);
  }

}
