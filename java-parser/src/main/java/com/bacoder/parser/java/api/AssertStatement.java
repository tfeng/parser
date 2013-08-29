package com.bacoder.parser.java.api;

public class AssertStatement extends Statement {

  private Expression errorMessage;

  private Expression expression;

  public Expression getErrorMessage() {
    return errorMessage;
  }

  public Expression getExpression() {
    return expression;
  }

  public void setErrorMessage(Expression errorMessage) {
    this.errorMessage = errorMessage;
  }

  public void setExpression(Expression expression) {
    this.expression = expression;
  }
}
