/**
 * Copyright 2013 Huining (Thomas) Feng (tfeng@berkeley.edu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bacoder.parser.core.test;

import org.antlr.v4.runtime.tree.ParseTree;
import org.testng.Assert;

import com.bacoder.parser.core.Adapter;
import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.core.Node;

public abstract class BaseTest {

  protected void assertAttributes(Node node, String input) {
    assertAttributes(node, input, input);
  }

  protected void assertAttributes(Node node, String input, String substring) {
    int substringIndex = input.indexOf(substring);
    String substringBefore = input.substring(0, substringIndex);
    int linesInSubstringBefore = countLines(substringBefore);
    int linesInSubstring = countLines(substring);
    int lastReturnInSubstringBefore = substringBefore.lastIndexOf("\n");
    int startLine = linesInSubstringBefore;
    int startIndex = substringBefore.length() - lastReturnInSubstringBefore - 1;
    int endLine = linesInSubstringBefore + linesInSubstring - 1;
    int endIndex;
    if (linesInSubstring > 1) {
      int lastReturnInSubstring = substring.lastIndexOf("\n");
      endIndex = substring.length() - lastReturnInSubstring - 2;
    } else {
      endIndex = startIndex + substring.length() - 1;
    }

    Assert.assertEquals(node.getStartLine(), startLine);
    Assert.assertEquals(node.getStartIndex(), startIndex);
    Assert.assertEquals(node.getEndLine(), endLine);
    Assert.assertEquals(node.getEndIndex(), endIndex);
  }

  protected <A extends Adapter<? extends ParseTree, ?>> A getAdapter(Class<A> clazz) {
    try {
      return clazz.getConstructor(Adapters.class).newInstance(getAdapters());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected abstract Adapters getAdapters();

  private int countLines(String input) {
    return input.length() - input.replace("\n", "").length() + 1;
  }
}
