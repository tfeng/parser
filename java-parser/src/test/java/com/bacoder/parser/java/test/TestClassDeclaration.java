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

import com.bacoder.parser.java.adapter.ClassDeclarationAdapter;
import com.bacoder.parser.java.api.ClassDeclaration;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.ClassDeclarationContext;

@Test
public class TestClassDeclaration extends JavaBaseTest {

  public void testSimpleClassDeclaration() {
    String input = "class SimpleClass {\n}";
    JavaParser parser = getParser(input);
    ClassDeclarationContext context = parser.classDeclaration();
    ClassDeclarationAdapter adapter = getAdapter(ClassDeclarationAdapter.class);
    ClassDeclaration classDeclaration = adapter.adapt(context);

    assertAttributes(classDeclaration, input);
    Assert.assertEquals(classDeclaration.getName().getText(), "SimpleClass");
    assertAttributes(classDeclaration.getName(), input, "SimpleClass");
    Assert.assertEquals(classDeclaration.getAnnotations(), Collections.emptyList());
    Assert.assertNull(classDeclaration.getExtendsType());
    Assert.assertEquals(classDeclaration.getImplementsTypes(), Collections.emptyList());
    Assert.assertEquals(classDeclaration.getTypeParameters(), Collections.emptyList());
  }
}
