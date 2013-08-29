package com.bacoder.parser.java.api;

public class Resource extends NodeWithModifiers {

  private Expression initializer;

  private Identifier name;

  private Type type;

  public Expression getInitializer() {
    return initializer;
  }

  public Identifier getName() {
    return name;
  }

  public Type getType() {
    return type;
  }

  public void setInitializer(Expression initializer) {
    this.initializer = initializer;
  }

  public void setName(Identifier name) {
    this.name = name;
  }

  public void setType(Type type) {
    this.type = type;
  }
}
