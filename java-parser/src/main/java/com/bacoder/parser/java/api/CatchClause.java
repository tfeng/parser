package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

public class CatchClause extends NodeWithModifiers {

  private Block body;

  private List<QualifiedName> exceptions = Collections.emptyList();

  private Identifier variable;

  public Block getBody() {
    return body;
  }

  public List<QualifiedName> getExceptions() {
    return exceptions;
  }

  public Identifier getVariable() {
    return variable;
  }

  public void setBody(Block body) {
    this.body = body;
  }

  public void setExceptions(List<QualifiedName> exceptions) {
    this.exceptions = exceptions;
  }

  public void setVariable(Identifier variable) {
    this.variable = variable;
  }
}
