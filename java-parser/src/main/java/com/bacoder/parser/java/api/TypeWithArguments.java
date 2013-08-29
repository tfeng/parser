package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

import com.bacoder.parser.core.Node;

public class TypeWithArguments extends Node implements InstantiableType {

  private List<IdentifierWithTypeArguments> identifiersWithTypeArguments = Collections.emptyList();

  public List<IdentifierWithTypeArguments> getIdentifiersWithTypeArguments() {
    return identifiersWithTypeArguments;
  }

  public void setIdentifiersWithTypeArguments(
      List<IdentifierWithTypeArguments> identifiersWithTypeArguments) {
    this.identifiersWithTypeArguments = identifiersWithTypeArguments;
  }
}
