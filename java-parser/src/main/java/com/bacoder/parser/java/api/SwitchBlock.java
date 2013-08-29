package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

public class SwitchBlock extends Statement {

  private List<SwitchLabel> labels = Collections.emptyList();

  private List<BlockStatement> statements = Collections.emptyList();

  public List<SwitchLabel> getLabels() {
    return labels;
  }

  public List<BlockStatement> getStatements() {
    return statements;
  }

  public void setLabels(List<SwitchLabel> labels) {
    this.labels = labels;
  }

  public void setStatements(List<BlockStatement> statements) {
    this.statements = statements;
  }
}
