package com.bacoder.parser.java.api;

public class IfStatement extends Statement {

  private Expression condition;

  private Statement elseStatement;

  private Statement thenStatement;

  public Expression getCondition() {
    return condition;
  }

  public Statement getElseStatement() {
    return elseStatement;
  }

  public Statement getThenStatement() {
    return thenStatement;
  }

  public void setCondition(Expression condition) {
    this.condition = condition;
  }

  public void setElseStatement(Statement elseStatement) {
    this.elseStatement = elseStatement;
  }

  public void setThenStatement(Statement thenStatement) {
    this.thenStatement = thenStatement;
  }
}
