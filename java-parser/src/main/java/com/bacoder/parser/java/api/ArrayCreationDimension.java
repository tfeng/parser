package com.bacoder.parser.java.api;

import com.bacoder.parser.core.Node;

public class ArrayCreationDimension extends Node {

  private Expression size;

  public Expression getSize() {
    return size;
  }

  public void setSize(Expression size) {
    this.size = size;
  }
}
