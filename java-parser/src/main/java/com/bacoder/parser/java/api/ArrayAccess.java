package com.bacoder.parser.java.api;

import com.bacoder.parser.core.Node;

public class ArrayAccess extends Node implements Expression {

  private Expression array;

  private Expression index;

  public Expression getArray() {
    return array;
  }

  public Expression getIndex() {
    return index;
  }

  public void setArray(Expression array) {
    this.array = array;
  }

  public void setIndex(Expression index) {
    this.index = index;
  }
}
