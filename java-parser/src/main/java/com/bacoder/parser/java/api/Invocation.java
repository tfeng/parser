package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

import com.bacoder.parser.core.Node;

public class Invocation extends Node implements Expression {

  private List<Expression> arguments = Collections.emptyList();

  private Expression scope;

  private List<? extends TypeArgument> typeArguments = Collections.emptyList();

  public List<Expression> getArguments() {
    return arguments;
  }

  public Expression getScope() {
    return scope;
  }

  public List<? extends TypeArgument> getTypeArguments() {
    return typeArguments;
  }

  public void setArguments(List<Expression> arguments) {
    this.arguments = arguments;
  }

  public void setScope(Expression scope) {
    this.scope = scope;
  }

  public void setTypeArguments(List<? extends TypeArgument> typeArguments) {
    this.typeArguments = typeArguments;
  }
}
