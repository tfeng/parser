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
import com.bacoder.parser.java.JavaParser;
import com.bacoder.parser.java.JavaParser.VariableDeclaratorContext;
import com.bacoder.parser.java.JavaParser.VariableDeclaratorIdContext;
import com.bacoder.parser.java.JavaParser.VariableInitializerContext;
import com.bacoder.parser.java.api.VariableDeclaration;

public class VariableDeclaratorAdapter
    extends JavaAdapter<VariableDeclaratorContext, VariableDeclaration> {

  public VariableDeclaratorAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public VariableDeclaration adapt(VariableDeclaratorContext context) {
    VariableDeclaration variableDeclarator = createNode(context);

    VariableDeclaratorIdContext variableDeclaratorIdContext =
        getChild(context, VariableDeclaratorIdContext.class);
    if (variableDeclaratorIdContext != null) {
      TerminalNode identifierNode =
          getTerminalNode(variableDeclaratorIdContext, JavaParser.Identifier);
      if (identifierNode != null) {
        variableDeclarator.setName(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
      }

      variableDeclarator.setDimensions(
          getAdapter(ArrayDimensionsAdapter.class).adapt(variableDeclaratorIdContext));
    }

    VariableInitializerContext variableInitializerContext =
        getChild(context, VariableInitializerContext.class);
    if (variableInitializerContext != null) {
      variableDeclarator.setInitializer(
          getAdapter(VariableInitializerAdapter.class).adapt(variableInitializerContext));
    }

    return variableDeclarator;
  }
}
