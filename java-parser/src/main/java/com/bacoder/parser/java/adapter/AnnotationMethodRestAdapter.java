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

import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.AnnotationMethodDeclaration;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.AnnotationMethodRestContext;
import com.srctran.backend.parser.java.JavaParser.DefaultValueContext;
import com.srctran.backend.parser.java.JavaParser.ElementValueContext;

public class AnnotationMethodRestAdapter
    extends JavaAdapter<AnnotationMethodRestContext, AnnotationMethodDeclaration> {

  public AnnotationMethodRestAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public AnnotationMethodDeclaration adapt(AnnotationMethodRestContext context) {
    AnnotationMethodDeclaration annotationMethodDeclaration = createNode(context);

    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      annotationMethodDeclaration.setName(
          getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    DefaultValueContext defaultValueContext = getChild(context, DefaultValueContext.class);
    if (defaultValueContext != null) {
      ElementValueContext elementValueContext =
          getChild(defaultValueContext, ElementValueContext.class);
      if (elementValueContext != null) {
        annotationMethodDeclaration.setDefaultValue(
            getAdapter(ElementValueAdapter.class).adapt(elementValueContext));
      }
    }

    return annotationMethodDeclaration;
  }
}
