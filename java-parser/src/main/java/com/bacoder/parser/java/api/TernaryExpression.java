package com.bacoder.parser.java.api;

import com.bacoder.parser.core.Node;

public class TernaryExpression extends Node implements Expression {

  private Expression condition;

  private Expression falseExpression;

  private Expression trueExpression;

  public Expression getCondition() {
    return condition;
  }

  public Expression getFalseExpression() {
    return falseExpression;
  }

  public Expression getTrueExpression() {
    return trueExpression;
  }

  public void setCondition(Expression condition) {
    this.condition = condition;
  }

  public void setFalseExpression(Expression falseExpression) {
    this.falseExpression = falseExpression;
  }

  public void setTrueExpression(Expression trueExpression) {
    this.trueExpression = trueExpression;
  }
}
