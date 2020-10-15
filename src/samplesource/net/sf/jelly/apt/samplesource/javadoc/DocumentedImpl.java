package net.sf.jelly.apt.samplesource.javadoc;

/**
 * @author Ryan Heaton
 */
public class DocumentedImpl implements DocumentedInterface {

  public String mymethod(String param1, String param2) throws IllegalStateException {
    return null;
  }

  /**
   * Here is the docs for mymethod2.
   *
   * @param param3 docs for param3
   * @param param4 docs for param4
   * @return docs for retval
   * @throws IllegalStateException docs for ise
   */
  public String mymethod2(String param3, String param4) throws IllegalStateException {
    return null;
  }

  /**
   * Here is the docs for mymethod3.
   *
   * @param param5 docs for param5
   * @param param6 docs for param6
   * @return docs for retval
   * @throws IllegalStateException docs for ise
   */
  public String mymethod3(String param5, String param6) throws IllegalStateException {
    return null;
  }
}
