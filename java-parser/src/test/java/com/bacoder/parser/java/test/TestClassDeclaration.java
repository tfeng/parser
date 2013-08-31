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

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.bacoder.parser.java.adapter.TypeDeclarationAdapter;
import com.bacoder.parser.java.api.ClassDeclaration;
import com.bacoder.parser.java.api.TypeDeclaration;
import com.bacoder.parser.java.JavaParser;
import com.bacoder.parser.java.JavaParser.TypeDeclarationContext;

@Test
public class TestClassDeclaration extends JavaBaseTest {

  public void testModifiers() {
    String input = "public protected private static abstract final strictfp class ClassWithModifiers {\n}";
    ClassDeclaration classDeclaration = parse(input);

    assertModifiers(classDeclaration, true, true, false, true, true, true, true, true, false, false,
        false);
    assertAttributes(classDeclaration, input);
    Assert.assertEquals(classDeclaration.getName().getText(), "ClassWithModifiers");
    assertAttributes(classDeclaration.getName(), input, "ClassWithModifiers");
    Assert.assertEquals(classDeclaration.getAnnotations(), Collections.emptyList());
    Assert.assertNull(classDeclaration.getExtendsType());
    Assert.assertEquals(classDeclaration.getImplementsTypes(), Collections.emptyList());
    Assert.assertEquals(classDeclaration.getTypeParameters(), Collections.emptyList());
  }

  public void testSimpleClassDeclaration() {
    String input = "class SimpleClass {\n}";
    ClassDeclaration classDeclaration = parse(input);

    assertModifiers(classDeclaration, false, false, false, false, false, false, false, false, false,
        false, false);
    assertAttributes(classDeclaration, input);
    Assert.assertEquals(classDeclaration.getName().getText(), "SimpleClass");
    assertAttributes(classDeclaration.getName(), input, "SimpleClass");
    Assert.assertEquals(classDeclaration.getAnnotations(), Collections.emptyList());
    Assert.assertNull(classDeclaration.getExtendsType());
    Assert.assertEquals(classDeclaration.getImplementsTypes(), Collections.emptyList());
    Assert.assertEquals(classDeclaration.getTypeParameters(), Collections.emptyList());
  }

  private ClassDeclaration parse(String input) {
    JavaParser parser = getParser(input);
    TypeDeclarationContext context = parser.typeDeclaration();
    TypeDeclarationAdapter adapter = getAdapter(TypeDeclarationAdapter.class);
    TypeDeclaration typeDeclaration = adapter.adapt(context);
    return (ClassDeclaration) typeDeclaration;
  }
}
