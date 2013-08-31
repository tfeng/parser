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
import java.util.Map;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.JavaParser;
import com.bacoder.parser.java.JavaParser.ArgumentsContext;
import com.bacoder.parser.java.JavaParser.ExplicitGenericInvocationSuffixContext;
import com.bacoder.parser.java.JavaParser.ExpressionContext;
import com.bacoder.parser.java.JavaParser.LiteralContext;
import com.bacoder.parser.java.JavaParser.NonWildcardTypeArgumentsContext;
import com.bacoder.parser.java.JavaParser.PrimaryContext;
import com.bacoder.parser.java.JavaParser.TypeContext;
import com.bacoder.parser.java.api.ClassExpression;
import com.bacoder.parser.java.api.Expression;
import com.bacoder.parser.java.api.Invocation;
import com.bacoder.parser.java.api.Literal;
import com.bacoder.parser.java.api.SuperExpression;
import com.bacoder.parser.java.api.ThisExpression;
import com.bacoder.parser.java.api.ThisInvocation;
import com.bacoder.parser.java.api.Type;
import com.bacoder.parser.java.api.VoidType;
import com.google.common.collect.ImmutableMap;

public class PrimaryExpressionAdapter extends JavaAdapter<PrimaryContext, Expression> {

  private static final Map<Integer, Literal.Type> LITERAL_TYPE_MAP =
      ImmutableMap.<Integer, Literal.Type>builder()
                  .put(JavaParser.BooleanLiteral, Literal.Type.BOOLEAN)
                  .put(JavaParser.CharacterLiteral, Literal.Type.CHARACTER)
                  .put(JavaParser.FloatingPointLiteral, Literal.Type.FLOATING_POINT)
                  .put(JavaParser.IntegerLiteral, Literal.Type.INTEGER)
                  .put(JavaParser.NullLiteral, Literal.Type.NULL)
                  .put(JavaParser.StringLiteral, Literal.Type.STRING)
                  .build();

  public PrimaryExpressionAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public Expression adapt(PrimaryContext context) {
    ExpressionContext expressionContext = getChild(context, ExpressionContext.class);
    if (expressionContext != null) {
      return getAdapter(ExpressionAdapter.class).adapt(expressionContext);
    }

    TerminalNode firstTerminal = getChild(context, TerminalNode.class);
    if (firstTerminal != null) {
      switch (firstTerminal.getSymbol().getType()) {
      case JavaParser.THIS:
        return createNode(context, ThisExpression.class);
      case JavaParser.SUPER:
        return createNode(context, SuperExpression.class);
      case JavaParser.Identifier:
        return getAdapter(IdentifierAdapter.class).adapt(firstTerminal);
      case JavaParser.VOID: {
        ClassExpression classExpression = createNode(context, ClassExpression.class);
        classExpression.setType(createNode(firstTerminal, VoidType.class));
        return classExpression;
      }
      default:
      }
    }

    LiteralContext literalContext = getChild(context, LiteralContext.class);
    if (literalContext != null) {
      Literal literal = createNode(context, Literal.class);

      TerminalNode terminal = getChild(literalContext, TerminalNode.class);
      if (terminal != null && LITERAL_TYPE_MAP.containsKey(terminal.getSymbol().getType())) {
        literal.setType(LITERAL_TYPE_MAP.get(terminal.getSymbol().getType()));
        literal.setText(terminal.getText());
      }

      return literal;
    }

    TypeContext typeContext = getChild(context, TypeContext.class);
    if (typeContext != null) {
      ClassExpression classExpression = createNode(context, ClassExpression.class);
      classExpression.setType(getAdapter(TypeAdapter.class).adapt(typeContext));
      return classExpression;
    }

    return processInvocationExpression(context);
  }

  protected Invocation processInvocationExpression(PrimaryContext context) {
    NonWildcardTypeArgumentsContext nonWildcardTypeArgumentsContext =
        getChild(context, NonWildcardTypeArgumentsContext.class);
    if (nonWildcardTypeArgumentsContext != null) {
      List<Type> typeList =
          getAdapter(NonWildcardTypeArgumentsAdapter.class).adapt(nonWildcardTypeArgumentsContext);

      ExplicitGenericInvocationSuffixContext explicitGenericInvocationSuffixContext =
          getChild(context, ExplicitGenericInvocationSuffixContext.class);
      if (explicitGenericInvocationSuffixContext != null) {
        return getAdapter(ExplicitGenericInvocationSuffixAdapter.class).adapt(
            explicitGenericInvocationSuffixContext, typeList);
      }

      if (hasTerminalNode(context, JavaParser.THIS)) {
        ThisInvocation thisInvocation = createNode(context, ThisInvocation.class);
        thisInvocation.setTypeArguments(typeList);

        ArgumentsContext argumentsContext = getChild(context, ArgumentsContext.class);
        if (argumentsContext != null) {
          thisInvocation.setArguments(
              getAdapter(ArgumentsAdapter.class).adapt(argumentsContext));
        }

        return thisInvocation;
      }
    }

    return null;
  }
}
