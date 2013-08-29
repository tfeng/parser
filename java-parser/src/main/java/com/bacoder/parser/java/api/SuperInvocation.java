package com.bacoder.parser.java.api;

public class SuperInvocation extends Invocation implements ExpressionInScope {

  private Identifier name;

  public Identifier getName() {
    return name;
  }

  public void setName(Identifier name) {
    this.name = name;
  }
}
