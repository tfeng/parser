package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

public class LocalVariableDeclaration extends NodeWithModifiers implements BlockStatement {

  private Type type;

  private List<VariableDeclaration> variableDeclarations = Collections.emptyList();

  public Type getType() {
    return type;
  }

  public List<VariableDeclaration> getVariableDeclarations() {
    return variableDeclarations;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public void setVariableDeclarations(List<VariableDeclaration> variableDeclarations) {
    this.variableDeclarations = variableDeclarations;
  }
}
