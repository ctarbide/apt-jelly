package net.sf.jelly.apt.samplesource.javadoc;

/**
 * @author Ryan Heaton
 */
public class DocumentedExt extends DocumentedImpl {

  @Override
  public String mymethod(String param1, String param2) throws IllegalStateException {
    return super.mymethod(param1, param2);
  }

  @Override
  public String mymethod2(String param3, String param4) throws IllegalStateException {
    return super.mymethod2(param3, param4);
  }

  /**
   * {@inheritDoc}
   */
  public String mymethod3(String param5, String param6) throws IllegalStateException {
    return null;
  }

  public String mymethod4(String param7, String param8) throws IllegalStateException {
    return null;
  }
}
