package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

public class Block extends Statement {

  private List<BlockStatement> statements = Collections.emptyList();

  public List<BlockStatement> getStatements() {
    return statements;
  }

  public void setStatements(List<BlockStatement> statements) {
    this.statements = statements;
  }
}
