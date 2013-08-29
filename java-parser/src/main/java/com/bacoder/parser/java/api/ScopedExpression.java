package com.bacoder.parser.java.api;

import com.bacoder.parser.core.Node;

public class ScopedExpression extends Node implements Expression {

  private ExpressionInScope expression;

  private Expression scope;

  public ExpressionInScope getExpression() {
    return expression;
  }

  public Expression getScope() {
    return scope;
  }

  public void setExpression(ExpressionInScope expression) {
    this.expression = expression;
  }

  public void setScope(Expression scope) {
    this.scope = scope;
  }
}
