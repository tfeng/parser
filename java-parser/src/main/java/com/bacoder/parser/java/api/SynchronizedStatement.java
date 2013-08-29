package com.bacoder.parser.java.api;

public class SynchronizedStatement extends Statement {

  private Block body;

  private Expression expression;

  public Block getBody() {
    return body;
  }

  public Expression getExpression() {
    return expression;
  }

  public void setBody(Block body) {
    this.body = body;
  }

  public void setExpression(Expression expression) {
    this.expression = expression;
  }
}
