package com.bacoder.parser.java.api;

public class DoWhileStatement extends Statement {

  private Expression condition;

  private Statement statement;

  public Expression getCondition() {
    return condition;
  }

  public Statement getStatement() {
    return statement;
  }

  public void setCondition(Expression condition) {
    this.condition = condition;
  }

  public void setStatement(Statement statement) {
    this.statement = statement;
  }
}
