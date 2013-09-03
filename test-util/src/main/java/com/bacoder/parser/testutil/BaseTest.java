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
package com.bacoder.parser.testutil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.antlr.v4.runtime.tree.ParseTree;
import org.testng.Assert;

import com.bacoder.parser.core.Adapter;
import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.core.DumpVisitors;
import com.bacoder.parser.core.Node;
import com.bacoder.parser.core.Pair;

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
    int startColumn = substringBefore.length() - lastReturnInSubstringBefore - 1;
    int endLine = linesInSubstringBefore + linesInSubstring - 1;
    int endColumn;
    if (linesInSubstring > 1) {
      int lastReturnInSubstring = substring.lastIndexOf("\n");
      endColumn = substring.length() - lastReturnInSubstring - 2;
    } else {
      endColumn = startColumn + substring.length() - 1;
    }

    Assert.assertEquals(node.getStartLine(), startLine);
    Assert.assertEquals(node.getStartColumn(), startColumn);
    Assert.assertEquals(node.getEndLine(), endLine);
    Assert.assertEquals(node.getEndColumn(), endColumn);
  }

  protected List<Pair<File, File>> findTestCases(String testRootPath,
      final String sourceExtension) {
    URL rootUrl = getClass().getClassLoader().getResource(testRootPath);
    File root = new File(rootUrl.getFile());
    final List<Pair<File, File>> testCases = new ArrayList<Pair<File, File>>();
    root.listFiles(new FileFilter() {
      @Override
      public boolean accept(File file) {
        if (file.isDirectory()) {
          File javaFile = new File(file, file.getName() + "." + sourceExtension);
          File xmlFile = new File(file, file.getName() + ".xml");
          if (javaFile.canRead() && xmlFile.canRead()) {
            testCases.add(new Pair<File, File>(javaFile, xmlFile));
          }
        }
        return false;
      }
    });
    return testCases;
  }

  protected <A extends Adapter<? extends ParseTree, ?>> A getAdapter(Class<A> clazz) {
    try {
      return clazz.getConstructor(Adapters.class).newInstance(getAdapters());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected abstract Adapters getAdapters();

  protected void verifyAST(Node node, File xmlFile)
      throws FileNotFoundException, UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    node.visit(new DumpVisitors(outputStream));
    String actual = outputStream.toString("UTF-8").trim();

    InputStream inputStream = new FileInputStream(xmlFile);
    Scanner scanner = new Scanner(inputStream, "UTF-8");
    String expected = "";
    try {
      expected = scanner.useDelimiter("\\Z").next().trim();
    } finally {
      scanner.close();
    }

    Assert.assertEquals(actual, expected);
  }

  private int countLines(String input) {
    return input.length() - input.replace("\n", "").length() + 1;
  }
}
