package com.bacoder.parser.java.api;

import com.bacoder.parser.core.Node;

public abstract class Statement extends Node implements BlockStatement {

  private Identifier label;

  public Identifier getLabel() {
    return label;
  }

  public void setLabel(Identifier label) {
    this.label = label;
  }
}
