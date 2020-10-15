package net.sf.jelly.apt.util;

/**
 * Tag handler that just strips the inline tags from the comments.
 *
 * @author Ryan Heaton
 */
public class BasicInlineTagStrippingHandler extends NoOpTagHandler {

  @Override
  public Object onInlineTag(String tagName, String tagText) {
    return tagText;
  }
}
