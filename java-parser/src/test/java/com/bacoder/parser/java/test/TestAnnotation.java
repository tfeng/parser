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

import com.bacoder.parser.java.adapter.AnnotationAdapter;
import com.bacoder.parser.java.api.Annotation;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.AnnotationContext;

@Test
public class TestAnnotation extends JavaBaseTest {

  public void testSimpleAnnotation() {
    String annotationName = "com.bacoder.annotation.MyAnnotation";
    String input = "@" + annotationName + "(1)";
    JavaParser parser = getParser(input);
    AnnotationContext context = parser.annotation();
    AnnotationAdapter adapter = getAdapter(AnnotationAdapter.class);
    Annotation annotation = adapter.adapt(context);

    assertAttributes(annotation, input);
    Assert.assertEquals(annotation.getName().getFullName(), annotationName);
    assertAttributes(annotation.getName(), input, annotationName);
    // FIXME: assertAttributes(annotation.getElementValue(), input, "1");
    Assert.assertEquals(annotation.getValues(), Collections.emptyList());
  }
}
