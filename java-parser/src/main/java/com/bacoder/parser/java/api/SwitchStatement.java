package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

public class SwitchStatement extends Statement {

  private Expression expression;

  private List<SwitchBlock> switchBlocks = Collections.emptyList();

  public List<SwitchBlock> getSwitchBlocks() {
    return switchBlocks;
  }

  public void setSwitchBlocks(List<SwitchBlock> switchBlocks) {
    this.switchBlocks = switchBlocks;
  }

  public Expression getExpression() {
    return expression;
  }

  public void setExpression(Expression expression) {
    this.expression = expression;
  }
}
