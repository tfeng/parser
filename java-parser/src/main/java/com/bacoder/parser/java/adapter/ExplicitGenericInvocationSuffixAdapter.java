package com.bacoder.parser.java.adapter;

import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.Invocation;
import com.bacoder.parser.java.api.MethodInvocation;
import com.bacoder.parser.java.api.SuperInvocation;
import com.bacoder.parser.java.api.Type;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.ArgumentsContext;
import com.srctran.backend.parser.java.JavaParser.ExplicitGenericInvocationSuffixContext;
import com.srctran.backend.parser.java.JavaParser.SuperSuffixContext;

public class ExplicitGenericInvocationSuffixAdapter
    extends JavaAdapter<ExplicitGenericInvocationSuffixContext, Invocation> {

  public ExplicitGenericInvocationSuffixAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public Invocation adapt(ExplicitGenericInvocationSuffixContext context) {
    return adapt(context, Collections.<Type>emptyList());
  }

  public Invocation adapt(ExplicitGenericInvocationSuffixContext context, List<Type> typeList) {
    TerminalNode firstTerminal = getChild(context, TerminalNode.class);
    if (firstTerminal != null) {
      switch (firstTerminal.getSymbol().getType()) {
      case JavaParser.SUPER: {
        SuperInvocation superInvocation = createData(context, SuperInvocation.class);
        superInvocation.setTypeArguments(typeList);

        SuperSuffixContext superSuffixContext = getChild(context, SuperSuffixContext.class);
        if (superSuffixContext != null) {
          superInvocation =
              getAdapter(SuperSuffixAdapter.class).adapt(superSuffixContext, superInvocation);
        }

        return superInvocation;
      }
      case JavaParser.Identifier: {
        MethodInvocation methodInvocation = createData(context, MethodInvocation.class);
        methodInvocation.setTypeArguments(typeList);

        methodInvocation.setName(getAdapter(IdentifierAdapter.class).adapt(firstTerminal));

        ArgumentsContext argumentsContext = getChild(context, ArgumentsContext.class);
        if (argumentsContext != null) {
          methodInvocation.setArguments(
              getAdapter(ArgumentsAdapter.class).adapt(argumentsContext));
        }

        return methodInvocation;
      }
      default:
      }
    }

    return null;
  }
}
