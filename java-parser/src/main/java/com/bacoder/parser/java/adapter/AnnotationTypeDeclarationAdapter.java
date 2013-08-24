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
import com.bacoder.parser.java.api.AnnotationMemberDeclaration;
import com.bacoder.parser.java.api.AnnotationDeclaration;
import com.google.common.base.Function;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.AnnotationTypeBodyContext;
import com.srctran.backend.parser.java.JavaParser.AnnotationTypeDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.AnnotationTypeElementDeclarationContext;

public class AnnotationTypeDeclarationAdapter
    extends JavaAdapter<AnnotationTypeDeclarationContext, AnnotationDeclaration> {

  public AnnotationTypeDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public AnnotationDeclaration adapt(AnnotationTypeDeclarationContext context) {
    AnnotationDeclaration annotationDeclaration = createData(context);

    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      annotationDeclaration.setName(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    AnnotationTypeBodyContext annotationTypeBodyContext =
        getChild(context, AnnotationTypeBodyContext.class);
    if (annotationTypeBodyContext != null) {
      annotationDeclaration.setBodyDeclarations(transform(annotationTypeBodyContext,
          AnnotationTypeElementDeclarationContext.class,
          new Function<AnnotationTypeElementDeclarationContext, AnnotationMemberDeclaration>() {
            @Override
            public AnnotationMemberDeclaration apply(
                AnnotationTypeElementDeclarationContext context) {
              return getAdapter(AnnotationTypeElementDeclarationAdapter.class).adapt(context);
            }
          }));
    }

    return annotationDeclaration;
  }
}
