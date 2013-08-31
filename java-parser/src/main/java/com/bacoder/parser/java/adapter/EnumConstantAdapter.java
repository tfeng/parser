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

import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.JavaParser;
import com.bacoder.parser.java.JavaParser.AnnotationContext;
import com.bacoder.parser.java.JavaParser.ArgumentsContext;
import com.bacoder.parser.java.JavaParser.ClassBodyContext;
import com.bacoder.parser.java.JavaParser.EnumConstantContext;
import com.bacoder.parser.java.api.Annotation;
import com.bacoder.parser.java.api.EnumConstant;
import com.google.common.base.Function;

public class EnumConstantAdapter extends JavaAdapter<EnumConstantContext, EnumConstant> {

  public EnumConstantAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public EnumConstant adapt(EnumConstantContext context) {
    EnumConstant enumConstant = createNode(context);

    List<Annotation> annotations =
        transform(context, AnnotationContext.class, new Function<AnnotationContext, Annotation>() {
          @Override
          public Annotation apply(AnnotationContext context) {
            return getAdapter(AnnotationAdapter.class).adapt(context);
          }
        });
    enumConstant.setAnnotations(annotations);

    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      enumConstant.setName(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    ArgumentsContext argumentsContext = getChild(context, ArgumentsContext.class);
    if (argumentsContext != null) {
      enumConstant.setArguments(getAdapter(ArgumentsAdapter.class).adapt(argumentsContext));
    }

    ClassBodyContext classBodyContext = getChild(context, ClassBodyContext.class);
    if (classBodyContext != null) {
      enumConstant.setMemberDeclarations(
          getAdapter(ClassBodyAdapter.class).adapt(classBodyContext));
    }

    return enumConstant;
  }
}
