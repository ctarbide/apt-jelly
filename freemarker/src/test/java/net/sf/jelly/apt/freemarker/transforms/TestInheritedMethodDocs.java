package net.sf.jelly.apt.freemarker.transforms;

import net.sf.jelly.apt.freemarker.FreemarkerTestCase;

import java.util.Properties;

/**
 * @author Ryan Heaton
 */
public class TestInheritedMethodDocs extends FreemarkerTestCase {

  /**
   * test inherited method docs.
   */
  public void testInheritedMethodDocs() throws Exception {
    runScript("testInheritedMethodDocs.fmt");
    Properties results = readOutputAsProperties("testInheritedMethodDocs.properties");
    assertEquals("Here is the documentation for mymethod.", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedImpl.methods.mymethod.javadoc"));
    assertEquals("docs for retval", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedImpl.methods.mymethod.returnType.javaDoc"));
    assertEquals("docs for param1", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedImpl.methods.mymethod.param0.javadoc"));
    assertEquals("docs for param2", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedImpl.methods.mymethod.param1.javadoc"));
    assertEquals("docs for ise", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedImpl.methods.mymethod.exception0.docComment"));

    assertEquals("Here is the docs for mymethod2.", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedImpl.methods.mymethod2.javadoc"));
    assertEquals("docs for retval", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedImpl.methods.mymethod2.returnType.javaDoc"));
    assertEquals("docs for param3", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedImpl.methods.mymethod2.param0.javadoc"));
    assertEquals("docs for param4", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedImpl.methods.mymethod2.param1.javadoc"));
    assertEquals("docs for ise", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedImpl.methods.mymethod2.exception0.docComment"));

    assertEquals("Here is the documentation for mymethod.", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod.javadoc"));
    assertEquals("docs for retval", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod.returnType.javaDoc"));
    assertEquals("docs for param1", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod.param0.javadoc"));
    assertEquals("docs for param2", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod.param1.javadoc"));
    assertEquals("docs for ise", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod.exception0.docComment"));

    assertEquals("Here is the docs for mymethod2.", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod2.javadoc"));
    assertEquals("docs for retval", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod2.returnType.javaDoc"));
    assertEquals("docs for param3", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod2.param0.javadoc"));
    assertEquals("docs for param4", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod2.param1.javadoc"));
    assertEquals("docs for ise", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod2.exception0.docComment"));

    assertEquals("Here is the docs for mymethod3.", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod3.javadoc"));
    assertEquals("docs for retval", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod3.returnType.javaDoc"));
    assertEquals("docs for param5", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod3.param0.javadoc"));
    assertEquals("docs for param6", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod3.param1.javadoc"));
    assertEquals("docs for ise", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod3.exception0.docComment"));

    assertEquals("", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod4.javadoc"));
    assertEquals("", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod4.returnType.javaDoc"));
    assertEquals("", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod4.param0.javadoc"));
    assertEquals("", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod4.param1.javadoc"));
    assertEquals("", results.getProperty("net.sf.jelly.apt.samplesource.javadoc.DocumentedExt.methods.mymethod4.exception0.docComment"));
  }
}
