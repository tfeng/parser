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
import com.bacoder.parser.java.JavaParser.ElementValueArrayInitializerContext;
import com.bacoder.parser.java.JavaParser.ElementValueContext;
import com.bacoder.parser.java.api.AnnotationArrayInitializer;
import com.bacoder.parser.java.api.AnnotationValue;
import com.google.common.base.Function;

public class ElementValueArrayInitializerAdapter
    extends JavaAdapter<ElementValueArrayInitializerContext, AnnotationArrayInitializer> {

  public ElementValueArrayInitializerAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public AnnotationArrayInitializer adapt(ElementValueArrayInitializerContext context) {
    AnnotationArrayInitializer annotationArrayInitializer = createNode(context);
    List<AnnotationValue> arrayElementValues =
        transform(context, ElementValueContext.class,
            new Function<ElementValueContext, AnnotationValue>() {
              @Override
              public AnnotationValue apply(ElementValueContext context) {
                return getAdapter(ElementValueAdapter.class).adapt(context);
              }
            });
    annotationArrayInitializer.setElementValues(arrayElementValues);
    return annotationArrayInitializer;
  }
}
