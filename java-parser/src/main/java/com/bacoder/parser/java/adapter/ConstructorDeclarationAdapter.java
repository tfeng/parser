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
import com.bacoder.parser.java.JavaParser.BlockContext;
import com.bacoder.parser.java.JavaParser.ConstructorBodyContext;
import com.bacoder.parser.java.JavaParser.ConstructorDeclarationContext;
import com.bacoder.parser.java.JavaParser.FormalParametersContext;
import com.bacoder.parser.java.JavaParser.QualifiedNameListContext;
import com.bacoder.parser.java.api.ConstructorDeclaration;

public class ConstructorDeclarationAdapter
    extends JavaAdapter<ConstructorDeclarationContext, ConstructorDeclaration> {

  public ConstructorDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public ConstructorDeclaration adapt(ConstructorDeclarationContext context) {
    ConstructorDeclaration constructorDeclaration = createNode(context);

    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      constructorDeclaration.setName(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    FormalParametersContext formalParametersContext =
        getChild(context, FormalParametersContext.class);
    if (formalParametersContext != null) {
      constructorDeclaration.setFormalParameters(
          getAdapter(FormalParametersAdapter.class).adapt(formalParametersContext));
    }

    QualifiedNameListContext qualifiedNameListContext =
        getChild(context, QualifiedNameListContext.class);
    if (qualifiedNameListContext != null) {
      constructorDeclaration.setThrowsExceptions(
          getAdapter(QualifiedNamesAdapter.class).adapt(qualifiedNameListContext));
    }

    ConstructorBodyContext constructorBodyContext = getChild(context, ConstructorBodyContext.class);
    if (constructorBodyContext != null) {
      BlockContext blockContext = getChild(constructorBodyContext, BlockContext.class);
      if (blockContext != null) {
        constructorDeclaration.setBody(getAdapter(BlockAdapter.class).adapt(blockContext));
      }
    }

    return constructorDeclaration;
  }
}
