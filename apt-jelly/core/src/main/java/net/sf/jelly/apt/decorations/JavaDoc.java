/*
 * Copyright 2006 Ryan Heaton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.jelly.apt.decorations;

import com.sun.mirror.declaration.Declaration;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Object encapsulating the javadocs of a declaration.  This class is intended
 * to provide more flexibility for accessing the docs than is provided with the
 * {@link Declaration#getDocComment()}.  It is implemented as a {@link Map} from
 * each javadoc tag to its list of values (as one javadoc tag can appear more than
 * once in a doc comment).
 *
 * @author Ryan Heaton
 */
public class JavaDoc extends HashMap<String, JavaDoc.JavaDocTagList> {

  private String value;

  public JavaDoc() {
    this(null);
  }

  public JavaDoc(String docComment) {
    if (docComment == null) {
      value = "";
    }
    else {
      BufferedReader reader = new BufferedReader(new StringReader(docComment));
      StringWriter currentValue = new StringWriter();
      PrintWriter out = new PrintWriter(currentValue);
      String currentTag = null;
      try {
        String line = reader.readLine();
        while (line != null) {
          line = line.trim();
          if (line.startsWith("@")) {
            //it's a javadoc block tag.
            pushValue(currentTag, currentValue.toString());

            int spaceIndex = line.indexOf(' ');
            if (spaceIndex == -1) {
              spaceIndex = line.length();
            }

            currentTag = line.substring(1, spaceIndex);
            String value = "";
            if ((spaceIndex + 1) < line.length()) {
              value = line.substring(spaceIndex + 1);
            }

            currentValue = new StringWriter();
            out = new PrintWriter(currentValue);
            out.println(value);
          }
          else {
            out.println(line);
          }

          line = reader.readLine();
        }
        
        pushValue(currentTag, currentValue.toString());
      }
      catch (IOException e) {
        //fall through.
      }
    }
  }

  /**
   * Pushes a value onto a tag.
   *
   * @param tag The tag onto which to push the value.  (null indicates no tag.)
   * @param value The value of the tag.
   */
  private void pushValue(String tag, String value) {
    value = value.trim(); //trim the value.
    
    if (tag == null) {
      this.value = value;
    }
    else {
      JavaDocTagList tagList = get(tag);
      if (tagList == null) {
        tagList = new JavaDocTagList(value);
        put(tag, tagList);
      }
      else {
        tagList.add(value);
      }
    }
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String toString() {
    return value;
  }

  /**
   * A list of values for a javadoc tag.
   */
  public static class JavaDocTagList extends ArrayList<String> {

    /**
     * To construct a tag list, at least one value must be supplied.
     *
     * @param firstValue The first value.
     */
    public JavaDocTagList(String firstValue) {
      add(firstValue);
    }

    /**
     * @return The first value in the list.
     */
    public String toString() {
      return get(0);
    }

  }

}
