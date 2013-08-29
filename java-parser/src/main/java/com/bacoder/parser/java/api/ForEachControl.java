package com.bacoder.parser.java.api;

public class ForEachControl extends NodeWithModifiers implements ForControl {

  private Expression iterable;

  private Type type;

  private Identifier variable;

  public Expression getIterable() {
    return iterable;
  }

  public Type getType() {
    return type;
  }

  public Identifier getVariable() {
    return variable;
  }

  public void setIterable(Expression iterable) {
    this.iterable = iterable;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public void setVariable(Identifier variable) {
    this.variable = variable;
  }
}
