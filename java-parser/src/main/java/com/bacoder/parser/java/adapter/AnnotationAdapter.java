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

import java.util.List;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.JavaParser.AnnotationContext;
import com.bacoder.parser.java.JavaParser.AnnotationNameContext;
import com.bacoder.parser.java.JavaParser.ElementValueContext;
import com.bacoder.parser.java.JavaParser.ElementValuePairContext;
import com.bacoder.parser.java.JavaParser.ElementValuePairsContext;
import com.bacoder.parser.java.JavaParser.QualifiedNameContext;
import com.bacoder.parser.java.api.Annotation;
import com.bacoder.parser.java.api.AnnotationValue;
import com.bacoder.parser.java.api.NameValuePair;
import com.google.common.base.Function;

public class AnnotationAdapter extends JavaAdapter<AnnotationContext, Annotation> {

  public AnnotationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public Annotation adapt(AnnotationContext context) {
    Annotation annotation = createNode(context);

    AnnotationNameContext annotationNameContext = getChild(context, AnnotationNameContext.class);
    if (annotationNameContext != null) {
      QualifiedNameContext qualifiedNameContext =
          getChild(annotationNameContext, QualifiedNameContext.class);
      if (qualifiedNameContext != null) {
        annotation.setName(getAdapter(QualifiedNameAdapter.class).adapt(qualifiedNameContext));
      }
    }

    ElementValuePairsContext elementValuePairsContext =
        getChild(context, ElementValuePairsContext.class);
    if (elementValuePairsContext != null) {
      List<NameValuePair> elementValuePairs =
          transform(elementValuePairsContext, ElementValuePairContext.class,
              new Function<ElementValuePairContext, NameValuePair>() {
                @Override
                public NameValuePair apply(ElementValuePairContext context) {
                  return getAdapter(ElementValuePairAdapter.class).adapt(context);
                }
              });
      annotation.setValues(elementValuePairs);
    }

    ElementValueContext elementValueContext = getChild(context, ElementValueContext.class);
    if (elementValueContext != null) {
      AnnotationValue elementValue =
          getAdapter(ElementValueAdapter.class).adapt(elementValueContext);
      annotation.setValue(elementValue);
    }

    return annotation;
  }
}
