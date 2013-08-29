package com.bacoder.parser.java.api;

import com.bacoder.parser.core.Node;

public class TypeCast extends Node implements Expression {

  private Expression expression;

  private Type type;

  public Expression getExpression() {
    return expression;
  }

  public Type getType() {
    return type;
  }

  public void setExpression(Expression expression) {
    this.expression = expression;
  }

  public void setType(Type type) {
    this.type = type;
  }
}
