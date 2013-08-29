package com.bacoder.parser.java.adapter;

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.ClassExpression;
import com.bacoder.parser.java.api.Expression;
import com.bacoder.parser.java.api.Invocation;
import com.bacoder.parser.java.api.SuperExpression;
import com.bacoder.parser.java.api.ThisExpression;
import com.bacoder.parser.java.api.ThisInvocation;
import com.bacoder.parser.java.api.Type;
import com.bacoder.parser.java.api.VoidType;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.ArgumentsContext;
import com.srctran.backend.parser.java.JavaParser.ExplicitGenericInvocationSuffixContext;
import com.srctran.backend.parser.java.JavaParser.ExpressionContext;
import com.srctran.backend.parser.java.JavaParser.NonWildcardTypeArgumentsContext;
import com.srctran.backend.parser.java.JavaParser.PrimaryContext;
import com.srctran.backend.parser.java.JavaParser.TypeContext;

public class PrimaryExpressionAdapter extends JavaAdapter<PrimaryContext, Expression> {

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
        return createData(context, ThisExpression.class);
      case JavaParser.SUPER:
        return createData(context, SuperExpression.class);
      case JavaParser.Identifier:
        return getAdapter(IdentifierAdapter.class).adapt(firstTerminal);
      case JavaParser.VOID: {
        ClassExpression classExpression = createData(context, ClassExpression.class);
        classExpression.setType(createData(firstTerminal, VoidType.class));
        return classExpression;
      }
      default:
      }
    }

    TypeContext typeContext = getChild(context, TypeContext.class);
    if (typeContext != null) {
      ClassExpression classExpression = createData(context, ClassExpression.class);
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
        ThisInvocation thisInvocation = createData(context, ThisInvocation.class);
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
