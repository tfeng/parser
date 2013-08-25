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
package com.bacoder.parser.java.test;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.Assert;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.adapter.JavaAdapters;
import com.bacoder.parser.java.api.NodeWithModifiers;
import com.bacoder.parser.java.api.QualifiedName;
import com.bacoder.parser.testutil.BaseTest;
import com.srctran.backend.parser.java.JavaLexer;
import com.srctran.backend.parser.java.JavaParser;

public abstract class JavaBaseTest extends BaseTest {

  protected void assertModifiers(NodeWithModifiers node, boolean isAbstract, boolean isFinal,
      boolean isNative, boolean isPrivate, boolean isProtected, boolean isPublic, boolean isStatic,
      boolean isStrictfp, boolean isSynchronized, boolean isTransient, boolean isVolatile) {
    Assert.assertEquals(node.isAbstract(), isAbstract);
    Assert.assertEquals(node.isFinal(), isFinal);
    Assert.assertEquals(node.isNative(), isNative);
    Assert.assertEquals(node.isPrivate(), isPrivate);
    Assert.assertEquals(node.isProtected(), isProtected);
    Assert.assertEquals(node.isPublic(), isPublic);
    Assert.assertEquals(node.isStatic(), isStatic);
    Assert.assertEquals(node.isStrictfp(), isStrictfp);
    Assert.assertEquals(node.isSynchronized(), isSynchronized);
    Assert.assertEquals(node.isTransient(), isTransient);
    Assert.assertEquals(node.isVolatile(), isVolatile);
  }

  protected void assertQualifiedName(QualifiedName qualifiedName, String input, String expected) {
    String[] parts = expected.split("\\.");
    for (int i = 0; i < parts.length; i++) {
      assertAttributes(qualifiedName.getIdentifiers().get(i), input, parts[i]);
    }
  }

  @Override
  protected Adapters getAdapters() {
    return new JavaAdapters();
  }

  protected JavaParser getParser(String input) {
    JavaLexer lexer = new JavaLexer(new ANTLRInputStream(input));
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    JavaParser parser = new JavaParser(tokenStream);
    return parser;
  }
}
