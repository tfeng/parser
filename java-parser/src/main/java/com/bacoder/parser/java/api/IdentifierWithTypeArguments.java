package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

import com.bacoder.parser.core.Node;

public class IdentifierWithTypeArguments extends Node {

  private Identifier identifier;

  private List<? extends TypeArgument> typeArguments = Collections.emptyList();

  public Identifier getIdentifier() {
    return identifier;
  }

  public List<? extends TypeArgument> getTypeArguments() {
    return typeArguments;
  }

  public void setIdentifier(Identifier identifier) {
    this.identifier = identifier;
  }

  public void setTypeArguments(List<? extends TypeArgument> typeArguments) {
    this.typeArguments = typeArguments;
  }
}
