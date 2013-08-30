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

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.ArrayType;
import com.bacoder.parser.java.api.FormalParameter;
import com.bacoder.parser.java.api.Type;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.TypeContext;
import com.srctran.backend.parser.java.JavaParser.VariableDeclaratorIdContext;

public abstract class AbstractFormalParameterAdapter<C extends ParserRuleContext,
    D extends FormalParameter> extends JavaAdapter<C, D> {

  public AbstractFormalParameterAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public D adapt(C context) {
    D formalParameter = createNode(context);

    setVariableModifiers(context, formalParameter);

    Type type = null;
    TypeContext typeContext = getChild(context, TypeContext.class);
    if (typeContext != null) {
      type = getAdapter(TypeAdapter.class).adapt(typeContext);
    }

    VariableDeclaratorIdContext variableDeclaratorIdContext =
        getChild(context, VariableDeclaratorIdContext.class);
    if (variableDeclaratorIdContext != null) {
      TerminalNode identifierNode =
          getTerminalNode(variableDeclaratorIdContext, JavaParser.Identifier);
      if (identifierNode != null) {
        formalParameter.setName(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
      }

      for (ParseTree node : context.children) {
        if (node instanceof TerminalNode
            && ((TerminalNode) node).getSymbol().getType() == JavaParser.RBRACK) {
          ArrayType arrayType = createNode(typeContext, node, ArrayType.class);
          arrayType.setElementType(type);
          type = arrayType;
        }
      }
    }

    formalParameter.setType(type);

    return formalParameter;
  }
}
