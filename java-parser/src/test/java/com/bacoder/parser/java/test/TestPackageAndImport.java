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

import java.util.Collections;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.core.test.BaseTest;
import com.bacoder.parser.java.adapter.JavaAdapters;
import com.bacoder.parser.java.adapter.PackageDeclarationAdapter;
import com.bacoder.parser.java.api.Annotation;
import com.bacoder.parser.java.api.NameValuePair;
import com.bacoder.parser.java.api.PackageDeclaration;
import com.srctran.backend.parser.java.JavaLexer;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.PackageDeclarationContext;

@Test
public class TestPackageAndImport extends BaseTest {

  public void testAnnotation() {
    String input = "@MyAnnotation(param = 1) package com;";
    JavaParser parser = getParser(input);
    PackageDeclarationContext packageDeclarationContext = parser.packageDeclaration();
    PackageDeclarationAdapter adapter = getAdapter(PackageDeclarationAdapter.class);
    PackageDeclaration packageDeclaration = adapter.adapt(packageDeclarationContext);

    assertAttributes(packageDeclaration, input);
    Assert.assertEquals(packageDeclaration.getQualifiedName().getFullName(), "com");
    assertAttributes(packageDeclaration.getQualifiedName(), input, "com");
    assertAttributes(packageDeclaration.getQualifiedName().getIdentifiers().get(0), input, "com");

    Assert.assertEquals(packageDeclaration.getAnnotations().size(), 1);
    Annotation annotation = packageDeclaration.getAnnotations().get(0);
    assertAttributes(annotation, input, "@MyAnnotation(param = 1)");
    Assert.assertEquals(annotation.getAnnotationName().getFullName(), "MyAnnotation");
    assertAttributes(annotation.getAnnotationName(), input, "MyAnnotation");
    Assert.assertEquals(annotation.getElementValue(), null);

    Assert.assertEquals(annotation.getNameValuePairs().size(), 1);
    NameValuePair nameValuePair = annotation.getNameValuePairs().get(0);
    assertAttributes(nameValuePair, input, "param = 1");
    Assert.assertEquals(nameValuePair.getIdentifier().getText(), "param");
    assertAttributes(nameValuePair.getIdentifier(), input, "param");
    // FIXME: Assert value.
    // FIXME: assertAttributes(nameValuePair.getValue(), input, "1");
  }

  public void testSimplePackage() {
    String packageName = "com.bacoder.parser.java.test";
    String input = "package " + packageName + ";";
    JavaParser parser = getParser(input);
    PackageDeclarationContext packageDeclarationContext = parser.packageDeclaration();
    PackageDeclarationAdapter adapter = getAdapter(PackageDeclarationAdapter.class);
    PackageDeclaration packageDeclaration = adapter.adapt(packageDeclarationContext);

    assertAttributes(packageDeclaration, input);
    Assert.assertEquals(packageDeclaration.getQualifiedName().getFullName(), packageName);
    assertAttributes(packageDeclaration.getQualifiedName(), input, packageName);
    String[] parts = packageName.split("\\.");
    for (int i = 0; i < parts.length; i++) {
      assertAttributes(packageDeclaration.getQualifiedName().getIdentifiers().get(i), input,
          parts[i]);
    }
    Assert.assertEquals(packageDeclaration.getAnnotations(), Collections.emptyList());
  }

  @Override
  protected Adapters getAdapters() {
    return new JavaAdapters();
  }

  private JavaParser getParser(String input) {
    JavaLexer lexer = new JavaLexer(new ANTLRInputStream(input));
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    JavaParser parser = new JavaParser(tokenStream);
    return parser;
  }
}
