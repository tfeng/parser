package com.bacoder.parser.java.api;

public class ForStatement extends Statement {

  private ForControl control;

  private Statement statement;

  public ForControl getControl() {
    return control;
  }

  public Statement getStatement() {
    return statement;
  }

  public void setControl(ForControl control) {
    this.control = control;
  }

  public void setStatement(Statement statement) {
    this.statement = statement;
  }
}
