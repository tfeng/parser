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
package com.bacoder.parser.java.adapter;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.AnnotationValue;
import com.srctran.backend.parser.java.JavaParser.AnnotationContext;
import com.srctran.backend.parser.java.JavaParser.ElementValueArrayInitializerContext;
import com.srctran.backend.parser.java.JavaParser.ElementValueContext;
import com.srctran.backend.parser.java.JavaParser.ExpressionContext;

public class ElementValueAdapter extends JavaAdapter<ElementValueContext, AnnotationValue> {

  public ElementValueAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public AnnotationValue adapt(ElementValueContext context) {
    ExpressionContext expressionContext = getChild(context, ExpressionContext.class);
    if (expressionContext != null) {
      return getAdapter(ExpressionAdapter.class).adapt(expressionContext);
    }

    AnnotationContext annotationContext = getChild(context, AnnotationContext.class);
    if (annotationContext != null) {
      return getAdapter(AnnotationAdapter.class).adapt(annotationContext);
    }

    ElementValueArrayInitializerContext elementValueArrayInitializerContext =
        getChild(context, ElementValueArrayInitializerContext.class);
    if (elementValueArrayInitializerContext != null) {
      return getAdapter(ElementValueArrayInitializerAdapter.class).adapt(
          elementValueArrayInitializerContext);
    }

    return null;
  }
}
