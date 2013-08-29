package com.bacoder.parser.java.adapter;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.SuperInvocation;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.ArgumentsContext;
import com.srctran.backend.parser.java.JavaParser.SuperSuffixContext;

public class SuperSuffixAdapter extends JavaAdapter<SuperSuffixContext, SuperInvocation> {

  public SuperSuffixAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public SuperInvocation adapt(SuperSuffixContext context) {
    throw new RuntimeException("Not supported");
  }

  public SuperInvocation adapt(SuperSuffixContext context, SuperInvocation superInvocation) {
    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      superInvocation.setName(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    ArgumentsContext argumentsContext = getChild(context, ArgumentsContext.class);
    if (argumentsContext != null) {
      superInvocation.setArguments(
          getAdapter(ArgumentsAdapter.class).adapt(argumentsContext));
    }

    return superInvocation;
  }
}
