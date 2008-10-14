package net.sf.jelly.apt.util;

/**
 * Basic tag handler that does no replacement.
 *
 * @author Ryan Heaton
 */
public class NoOpTagHandler implements JavaDocTagHandler {

  public Object onInlineTag(String tagName, String tagText) {
    return null;
  }

  public Object onMarkupTag(String tagName, String tagBody) {
    return null;
  }
}
