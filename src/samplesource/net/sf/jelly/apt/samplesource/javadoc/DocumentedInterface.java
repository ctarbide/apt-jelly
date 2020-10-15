package net.sf.jelly.apt.samplesource.javadoc;

/**
 * @author Ryan Heaton
 */
public interface DocumentedInterface {

  /**
   * Here is the documentation for mymethod.
   *
   * @param param1 docs for param1
   * @param param2 docs for param2
   * @return docs for retval
   * @throws IllegalStateException docs for ise
   */
  public String mymethod(String param1, String param2) throws IllegalStateException;
}
