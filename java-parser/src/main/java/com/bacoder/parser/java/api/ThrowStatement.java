package com.bacoder.parser.java.api;

public class ThrowStatement extends Statement {

  private Expression expression;

  public Expression getExpression() {
    return expression;
  }

  public void setExpression(Expression expression) {
    this.expression = expression;
  }
}
