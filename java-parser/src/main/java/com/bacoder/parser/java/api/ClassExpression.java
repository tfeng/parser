package com.bacoder.parser.java.api;

import com.bacoder.parser.core.Node;

public class ClassExpression extends Node implements Expression {

  private ReturnType type;

  public ReturnType getType() {
    return type;
  }

  public boolean isVoid() {
    return type instanceof VoidType;
  }

  public void setType(ReturnType type) {
    this.type = type;
  }
}
