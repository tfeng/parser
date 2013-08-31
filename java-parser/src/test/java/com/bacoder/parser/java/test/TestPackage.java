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

import org.testng.Assert;
import org.testng.annotations.Test;

import com.bacoder.parser.java.adapter.PackageDeclarationAdapter;
import com.bacoder.parser.java.api.Annotation;
import com.bacoder.parser.java.api.NameValuePair;
import com.bacoder.parser.java.api.PackageDeclaration;
import com.bacoder.parser.java.JavaParser;
import com.bacoder.parser.java.JavaParser.PackageDeclarationContext;

@Test
public class TestPackage extends JavaBaseTest {

  public void testAnnotation() {
    String input = "@MyAnnotation(param = 1) package com;";
    JavaParser parser = getParser(input);
    PackageDeclarationContext context = parser.packageDeclaration();
    PackageDeclarationAdapter adapter = getAdapter(PackageDeclarationAdapter.class);
    PackageDeclaration packageDeclaration = adapter.adapt(context);

    assertAttributes(packageDeclaration, input);
    Assert.assertEquals(packageDeclaration.getQualifiedName().getFullName(), "com");
    assertAttributes(packageDeclaration.getQualifiedName(), input, "com");
    assertAttributes(packageDeclaration.getQualifiedName().getIdentifiers().get(0), input, "com");

    Assert.assertEquals(packageDeclaration.getAnnotations().size(), 1);
    Annotation annotation = packageDeclaration.getAnnotations().get(0);
    assertAttributes(annotation, input, "@MyAnnotation(param = 1)");
    Assert.assertEquals(annotation.getName().getFullName(), "MyAnnotation");
    assertAttributes(annotation.getName(), input, "MyAnnotation");
    Assert.assertEquals(annotation.getValue(), null);

    Assert.assertEquals(annotation.getValues().size(), 1);
    NameValuePair nameValuePair = annotation.getValues().get(0);
    assertAttributes(nameValuePair, input, "param = 1");
    Assert.assertEquals(nameValuePair.getName().getText(), "param");
    assertAttributes(nameValuePair.getName(), input, "param");
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
    assertQualifiedName(packageDeclaration.getQualifiedName(), input, packageName);
    Assert.assertEquals(packageDeclaration.getAnnotations(), Collections.emptyList());
  }
}
