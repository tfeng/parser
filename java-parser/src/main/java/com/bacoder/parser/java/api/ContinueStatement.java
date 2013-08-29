package com.bacoder.parser.java.api;

public class ContinueStatement extends Statement {

  private Identifier identifier;

  public Identifier getIdentifier() {
    return identifier;
  }

  public void setIdentifier(Identifier identifier) {
    this.identifier = identifier;
  }
}
