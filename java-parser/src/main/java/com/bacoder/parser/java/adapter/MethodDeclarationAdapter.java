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

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.ArrayType;
import com.bacoder.parser.java.api.MethodDeclaration;
import com.bacoder.parser.java.api.Type;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.BlockContext;
import com.srctran.backend.parser.java.JavaParser.FormalParametersContext;
import com.srctran.backend.parser.java.JavaParser.MethodBodyContext;
import com.srctran.backend.parser.java.JavaParser.MethodDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.QualifiedNameListContext;
import com.srctran.backend.parser.java.JavaParser.TypeContext;

public class MethodDeclarationAdapter
    extends JavaAdapter<MethodDeclarationContext, MethodDeclaration> {

  public MethodDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public MethodDeclaration adapt(MethodDeclarationContext context) {
    MethodDeclaration methodDeclaration = createData(context);

    Type type = null;
    TypeContext typeContext = getChild(context, TypeContext.class);
    if (typeContext != null) {
      type = getAdapter(TypeAdapter.class).adapt(typeContext);
    }

    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      methodDeclaration.setName(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    FormalParametersContext formalParametersContext =
        getChild(context, FormalParametersContext.class);
    if (formalParametersContext != null) {
      methodDeclaration.setFormalParameters(
          getAdapter(FormalParametersAdapter.class).adapt(formalParametersContext));
    }

    for (ParseTree node : context.children) {
      if (node instanceof TerminalNode
          && ((TerminalNode) node).getSymbol().getType() == JavaParser.LBRACK) {
        ArrayType arrayType = createData(ArrayType.class, typeContext, node);
        arrayType.setElementType(type);
        type = arrayType;
      }
    }
    methodDeclaration.setReturnType(type);

    QualifiedNameListContext qualifiedNameListContext =
        getChild(context, QualifiedNameListContext.class);
    if (qualifiedNameListContext != null) {
      methodDeclaration.setThrowsExceptions(
          getAdapter(QualifiedNamesAdapter.class).adapt(qualifiedNameListContext));
    }

    MethodBodyContext methodBodyContext = getChild(context, MethodBodyContext.class);
    if (methodBodyContext != null) {
      BlockContext blockContext = getChild(methodBodyContext, BlockContext.class);
      methodDeclaration.setBody(getAdapter(BlockAdapter.class).adapt(blockContext));
    }

    return methodDeclaration;
  }
}
