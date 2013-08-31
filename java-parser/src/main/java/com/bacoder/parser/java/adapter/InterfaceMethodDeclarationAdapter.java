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
import com.bacoder.parser.java.JavaParser;
import com.bacoder.parser.java.JavaParser.FormalParametersContext;
import com.bacoder.parser.java.JavaParser.InterfaceMethodDeclarationContext;
import com.bacoder.parser.java.JavaParser.QualifiedNameListContext;
import com.bacoder.parser.java.JavaParser.TypeContext;
import com.bacoder.parser.java.api.ArrayType;
import com.bacoder.parser.java.api.InterfaceMethodDeclaration;
import com.bacoder.parser.java.api.Type;
import com.bacoder.parser.java.api.TypeOrVoid;
import com.bacoder.parser.java.api.VoidType;

public class InterfaceMethodDeclarationAdapter
    extends JavaAdapter<InterfaceMethodDeclarationContext, InterfaceMethodDeclaration> {

  public InterfaceMethodDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public InterfaceMethodDeclaration adapt(InterfaceMethodDeclarationContext context) {
    InterfaceMethodDeclaration interfaceDeclaration = createNode(context);

    TypeOrVoid type = null;
    TypeContext typeContext = getChild(context, TypeContext.class);
    if (typeContext != null) {
      type = getAdapter(TypeAdapter.class).adapt(typeContext);
    } else {
      TerminalNode voidNode = getTerminalNode(context, JavaParser.VOID);
      if (voidNode != null) {
        type = createNode(voidNode, VoidType.class);
      }
    }

    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      interfaceDeclaration.setName(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    FormalParametersContext formalParametersContext =
        getChild(context, FormalParametersContext.class);
    if (formalParametersContext != null) {
      interfaceDeclaration.setFormalParameters(
          getAdapter(FormalParametersAdapter.class).adapt(formalParametersContext));
    }

    for (ParseTree node : context.children) {
      if (!(type instanceof Type)) {
        break;
      }
      if (node instanceof TerminalNode
          && ((TerminalNode) node).getSymbol().getType() == JavaParser.RBRACK) {
        ArrayType arrayType = createNode(typeContext, node, ArrayType.class);
        arrayType.setElementType((Type) type);
        type = arrayType;
      }
    }
    interfaceDeclaration.setReturnType(type);

    QualifiedNameListContext qualifiedNameListContext =
        getChild(context, QualifiedNameListContext.class);
    if (qualifiedNameListContext != null) {
      interfaceDeclaration.setThrowsExceptions(
          getAdapter(QualifiedNamesAdapter.class).adapt(qualifiedNameListContext));
    }

    return interfaceDeclaration;
  }
}
