package com.bacoder.parser.java.api;

import com.bacoder.parser.core.Node;

public class PrefixExpression extends Node implements Expression {

  public static enum Operator {
    DECREMENT("--"),
    INCREMENT("++"),
    INVERT("~"),
    NEGATIVE("-"),
    NOT("!"),
    POSITIVE("+");

    private String operator;

    private Operator(String operator) {
      this.operator = operator;
    }

    @Override
    public String toString() {
      return operator;
    }
  }

  private Expression expression;

  private Operator operator;

  public Expression getExpression() {
    return expression;
  }

  public Operator getOperator() {
    return operator;
  }

  public void setExpression(Expression expression) {
    this.expression = expression;
  }

  public void setOperator(Operator operator) {
    this.operator = operator;
  }
}
