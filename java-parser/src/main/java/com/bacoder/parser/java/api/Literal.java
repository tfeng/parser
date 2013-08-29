package com.bacoder.parser.java.api;

import com.bacoder.parser.core.Node;

public class Literal extends Node implements Expression {

  public static enum Type {
    BOOLEAN,
    CHARACTER,
    FLOATING_POINT,
    INTEGER,
    NULL,
    STRING
  }

  private Type type;

  private String value;

  public Type getType() {
    return type;
  }

  public String getValue() {
    return value;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
