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
import com.bacoder.parser.java.api.Type;
import com.bacoder.parser.java.api.VariableDeclaration;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.ConstantDeclaratorContext;
import com.srctran.backend.parser.java.JavaParser.VariableInitializerContext;

public class ConstDeclaratorAdapter
    extends JavaAdapter<ConstantDeclaratorContext, VariableDeclaration> {

  public ConstDeclaratorAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public VariableDeclaration adapt(ConstantDeclaratorContext context) {
    throw new RuntimeException("Not supported");
  }

  public VariableDeclaration adapt(ConstantDeclaratorContext context, Type baseType,
      ParseTree baseTypeContext) {
    VariableDeclaration variableDeclarator = createNode(context);

    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      variableDeclarator.setName(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    Type type = baseType;
    for (ParseTree node : context.children) {
      if (node instanceof TerminalNode
          && ((TerminalNode) node).getSymbol().getType() == JavaParser.RBRACK) {
        ArrayType arrayType = createNode(baseTypeContext, node, ArrayType.class);
        arrayType.setElementType(type);
        type = arrayType;
      }
    }
    variableDeclarator.setType(type);

    VariableInitializerContext variableInitializerContext =
        getChild(context, VariableInitializerContext.class);
    if (variableInitializerContext != null) {
      variableDeclarator.setInitializer(
          getAdapter(VariableInitializerAdapter.class).adapt(variableInitializerContext));
    }

    return variableDeclarator;
  }
}
