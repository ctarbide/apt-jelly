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

package net.sf.jelly.apt.strategies;

import java.util.Stack;

/**
 * A stack for pushing the current strategy.
 *
 * @author Ryan Heaton
 */
public class StrategyStack extends Stack<TemplateStrategy> {

  private static final ThreadLocal<StrategyStack> local = new ThreadLocal<StrategyStack>() {
    @Override
    protected StrategyStack initialValue() {
      return new StrategyStack();
    }

    @Override
    public void set(StrategyStack value) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  };

  private StrategyStack() {
  }

  /**
   * The current stack.
   * 
   * @return The current stack.
   */
  public static StrategyStack get() {
    return local.get();
  }

}
